package com.epam.edu.kh.business.social.reader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.service.record.RecordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("socialReaderVk")
public class SocialReaderVK implements SocialReader {

    @Autowired
    @Qualifier("recordServiceImpl")
    private RecordService recordService;

    @Value("${tag}")
    private String tag;

    private static final Logger log = LoggerFactory.getLogger(SocialReaderVK.class);

    public final List<Record> getNewRecords() {

        List<Record> newRecords = new ArrayList<Record>();

        byte[] jsonData;
        try {
            jsonData = getJsonResponse(getDateOfLastInsertedRecord()).getBytes(Charset.forName("UTF-8"));

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode;

            rootNode = objectMapper.readTree(jsonData);

            if (rootNode.get("response") != null) {
                Iterator<JsonNode> elementsOfResponse = rootNode.get("response").iterator();
                JsonNode element = elementsOfResponse.next();
                while (elementsOfResponse.hasNext()) {
                    
                    element = elementsOfResponse.next();
                    
                    Long date;
                    String params[] = getParams(element);
                    date = element.path("date").asLong();
                    newRecords.add(new Record(1, params[0], params[1], "vk", params[2], params[3], params[4], params[5], date));
                }
            }

        } catch (JsonProcessingException e) {
            log.error("Failed to read json", e);
        } catch (IOException e) {
            log.error("Cannot get response", e);
        }
        log.info("Records received from vk:" + newRecords.size());
        return newRecords;
    }

    private String[] getParams(JsonNode element) {
        String params[] = new String[6];

        String userName = "";
        String userProfileUrl = "";
        String profilePhotoUrl = "";
        String recordPhotoUrl = "";
        String message = "";
        String user = "user";
        String group = "group";
        String attacment = "attacment";
        if (element.path(user).findValue("first_name") != null) {

            userName = element.path(user).findValue("first_name").asText() + " ";
            userName = userName.concat(element.path(user).findValue("last_name").asText());
            userProfileUrl = "http://vk.com/" + element.path(user).findValue("screen_name").asText();
            profilePhotoUrl = element.path(user).findValue("photo_medium_rec").asText();

        } else {
            userName = element.path(group).findValue("name").asText();
            userProfileUrl = "http://vk.com/" + element.path(group).findValue("screen_name").asText();
            profilePhotoUrl = element.path(group).findValue("photo_medium").asText();
        }
        String sourceUrl = "http://vk.com/wall" + element.findValue("owner_id") + "_" + element.findValue("id");

        if (element.get(attacment) != null) {
            if (element.get(attacment).get("photo") != null) {
                recordPhotoUrl = element.get(attacment).get("photo").get("src_big").asText();
            }
        }

        if (element.get("post_type").asText().equals("post")) {
            message = element.get("text").asText();
        } else if (element.get("copy_text") != null) {
            message = element.get("copy_text").asText();
        }

        params[0] = userName;
        params[1] = sourceUrl;
        params[2] = userProfileUrl;
        params[3] = profilePhotoUrl;
        params[4] = message;
        params[5] = recordPhotoUrl;

        return params;
    }

    private String getDateOfLastInsertedRecord() {
        String startTime;
        Long dateOfLastInsertedRecord = recordService.getDateOfLastInsertedRecord("vk");
        if (dateOfLastInsertedRecord != null) {
            startTime = String.valueOf(dateOfLastInsertedRecord + 1);
        } else {
            startTime = "";
        }
        return startTime;
    }

    private String getJsonResponse(String dateOfLastInsertedRecord) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String response = "";
        try {
            HttpGet httpget = new HttpGet("https://ai.vk.com/method/newsfeed.search" + "?q=%23" + tag + "&extended=1" + "&start_time="
                    + dateOfLastInsertedRecord);

            ResponseHandler<String> respHand = new ResponseHandler<String>() {

                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            response = httpclient.execute(httpget, respHand);
        } finally {
            httpclient.close();
        }
        log.info("Date of last inserted record of vk:" + dateOfLastInsertedRecord);
        return response;
    }
}
