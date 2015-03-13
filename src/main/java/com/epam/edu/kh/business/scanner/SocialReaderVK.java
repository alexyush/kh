package com.epam.edu.kh.business.scanner;

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

    public final String getResponse(String tag, String strDate)
            throws ClientProtocolException, IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String json = "";
        try {
            HttpGet httpget = new HttpGet(
                    "https://api.vk.com/method/newsfeed.search" + "?q=%23"
                            + tag + "&extended=1" + "&start_time=" + strDate);

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
            json = httpclient.execute(httpget, respHand);
        } finally {
            httpclient.close();
        }
        return json;
    }

    public final List<Record> parseResponse(String tag, String startTime)
            throws ClientProtocolException, IOException {

        List<Record> newRecords = new ArrayList<Record>();
        byte[] jsonData = getResponse(tag, startTime).getBytes(
                Charset.forName("UTF-8"));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);

        if (rootNode.get("response").size() != 0) {
            Iterator<JsonNode> js = rootNode.get("response").elements();
            JsonNode el = js.next();
            while (js.hasNext()) {

                String name = "";
                String userProfileUrl = "";
                String sourceUrl = "";
                String profilePhotoUrl = "";
                String recordPhotoUrl = "";
                String message = "";
                Long date;

                el = js.next();
                date = el.path("date").asLong();
                if (el.path("user").findValue("first_name") != null) {

                    name = el.path("user").findValue("first_name").asText()
                            + " ";
                    name = name.concat(el.path("user").findValue("last_name")
                            .asText());
                    userProfileUrl = "http://vk.com/"
                            + el.path("user").findValue("screen_name").asText();
                    profilePhotoUrl = el.path("user")
                            .findValue("photo_medium_rec").asText();
                } else {
                    name = el.path("group").findValue("name").asText();
                    userProfileUrl = "http://vk.com/"
                            + el.path("group").findValue("screen_name")
                                    .asText();
                    profilePhotoUrl = el.path("group")
                            .findValue("photo_medium").asText();
                }
                sourceUrl = "http://vk.com/wall" + el.findValue("owner_id")
                        + "_" + el.findValue("id");

                if (el.get("attachment") != null) {
                    if (el.get("attachment").get("photo") != null) {
                        recordPhotoUrl = el.get("attachment").get("photo")
                                .get("src_big").asText();
                    }
                }
                if (el.get("post_type").asText().equals("post")) {
                    message = el.get("text").asText();
                } else {
                    message = el.get("copy_text").asText();
                }
                newRecords.add(new Record(1, name, sourceUrl, userProfileUrl,
                        profilePhotoUrl, message, recordPhotoUrl, date));
            }
        }
        return newRecords;
    }

    public final void getAndSaveNewRecords(String tag, String startTime) {

        List<Record> newRecords;

        try {
            newRecords = parseResponse(tag, startTime);

            Iterator<Record> newRecordsIt = newRecords.iterator();
            while (newRecordsIt.hasNext()) {

                try {
                    recordService.insertRecord(newRecordsIt.next());
                } catch (JsonProcessingException e) {
                    System.out.println("Error:" + e);
                } catch (IOException e) {
                    System.out.println("Error:" + e);
                }
            }
        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public final Record getNewestDataForUpdate(Record record)
            throws ClientProtocolException, IOException {

        int index = record.getSourceUrl().lastIndexOf("http://vk.com/wall");
        index += "http://vk.com/wall".length();
        String postId = record.getSourceUrl().substring(index);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String jsonData = "";
        try {
            HttpGet httpget = new HttpGet(
                    "http://api.vk.com/method/wall.getById?posts=" + postId
                            + "&extended=1&");

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
            jsonData = httpclient.execute(httpget, respHand);
        } finally {
            httpclient.close();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);

        JsonNode wall = rootNode.get("response").get("wall");
        JsonNode insideWall = rootNode.get("response").get("wall").get(0);
        String message = "";
        String recordPhotoUrl = "";

        if (wall.findValue("copy_text") != null
                && wall.findValue("text").asText().contains("#ДобраеСэрца")) {

            message = rootNode.get("response").get("wall")
                    .findValue("copy_text").asText();

        } else if (wall.findValue("text").asText().contains("#ДобраеСэрца")) {
            message = rootNode.get("response").get("wall").findValue("text")
                    .asText();
        } else {
            throw new NullPointerException("Tag kind-heart are lost");
        }
        if (insideWall != null) {
            if (insideWall.get("attachment") != null) {
                recordPhotoUrl = insideWall.get("attachment")
                        .findValue("src_big").asText();
            }
        }
        /*record.setId((record.getId()));
        record.setSourceUrl(record.getSourceUrl());
        record.setUserName(record.getUserName());
        record.setUserPhotoUrl(record.getUserPhotoUrl());
        record.setUserProfileUrl(record.getUserProfileUrl());
        record.setDateOfCreate(record.getDateOfCreate());*/
        record.setMessage(message);
        record.setRecordPhotoUrl(recordPhotoUrl);
        return record;
    }
}
