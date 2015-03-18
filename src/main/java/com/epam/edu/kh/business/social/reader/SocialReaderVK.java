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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    public SocialReaderVK() {

    }

    private String getResponseForAddNewRecordsByTag(String tag)
            throws ClientProtocolException, IOException {

        String startTime;
        Long dateOfLastInsertedRecord = recordService
                .getDateOfLastInsertedRecord();
        if (dateOfLastInsertedRecord != null) {

            startTime = String.valueOf(dateOfLastInsertedRecord + 1);

        } else {
            startTime = "";
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String response = "";
        try {
            HttpGet httpget = new HttpGet(
                    "https://api.vk.com/method/newsfeed.search" + "?q=%23"
                            + tag + "&extended=1" + "&start_time=" + startTime);

            ResponseHandler<String> respHand = new ResponseHandler<String>() {

                public String handleResponse(final HttpResponse response)
                        throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity)
                                : null;
                    } else {
                        throw new ClientProtocolException(
                                "Unexpected response status: " + status);
                    }
                }
            };
            response = httpclient.execute(httpget, respHand);
        } finally {
            httpclient.close();
        }
        return response;
    }

    public final List<Record> getNewRecordsByTag(String tag) {

        List<Record> newRecords = new ArrayList<Record>();

        byte[] jsonData;
        try {
            jsonData = getResponseForAddNewRecordsByTag(tag).getBytes(
                    Charset.forName("UTF-8"));

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode;

            rootNode = objectMapper.readTree(jsonData);

            if (rootNode.get("response") != null) {
                Iterator<JsonNode> elementsOfResponse = rootNode
                        .get("response").elements();
                JsonNode element = elementsOfResponse.next();
                while (elementsOfResponse.hasNext()) {

                    String name = "";
                    String userProfileUrl = "";
                    String sourceUrl = "";
                    String profilePhotoUrl = "";
                    String recordPhotoUrl = "";
                    String message = "";
                    Long date;

                    element = elementsOfResponse.next();
                    date = element.path("date").asLong();
                    if (element.path("user").findValue("first_name") != null) {

                        name = element.path("user").findValue("first_name")
                                .asText()
                                + " ";
                        name = name.concat(element.path("user")
                                .findValue("last_name").asText());
                        userProfileUrl = "http://vk.com/"
                                + element.path("user").findValue("screen_name")
                                        .asText();
                        profilePhotoUrl = element.path("user")
                                .findValue("photo_medium_rec").asText();
                    } else {
                        name = element.path("group").findValue("name").asText();
                        userProfileUrl = "http://vk.com/"
                                + element.path("group")
                                        .findValue("screen_name").asText();
                        profilePhotoUrl = element.path("group")
                                .findValue("photo_medium").asText();
                    }
                    sourceUrl = "http://vk.com/wall"
                            + element.findValue("owner_id") + "_"
                            + element.findValue("id");

                    if (element.get("attachment") != null) {
                        if (element.get("attachment").get("photo") != null) {
                            recordPhotoUrl = element.get("attachment")
                                    .get("photo").get("src_big").asText();
                        }
                    }
                    if (element.get("post_type").asText().equals("post")) {
                        message = element.get("text").asText();
                    } else {
                        message = element.get("copy_text").asText();
                    }
                    newRecords.add(new Record(1, name, sourceUrl, "vk",
                            userProfileUrl, profilePhotoUrl, message,
                            recordPhotoUrl, date));
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Count of new records:" + newRecords.size());
        return newRecords;
    }

}
