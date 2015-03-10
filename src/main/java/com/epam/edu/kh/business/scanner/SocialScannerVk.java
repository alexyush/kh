package com.epam.edu.kh.business.scanner;

import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Component;
import com.epam.edu.kh.business.entity.Record;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("socialScannerVk")
public class SocialScannerVk implements SocialScanner {

    public SocialScannerVk() {
    }

    public final String parseLink(final String linkToResource)
            throws NotFoundException {

        final String wall_link_type_1 = "?w=wall";
        final String wall_link_type_2 = "http://vk.com/wall";

        String link = "";
        if (linkToResource.contains(wall_link_type_1)) {

            int index = linkToResource.lastIndexOf(wall_link_type_1);
            index += wall_link_type_1.length();
            link = linkToResource.substring(index);

        } else if (linkToResource.contains(wall_link_type_2)) {
            int index = linkToResource.lastIndexOf(wall_link_type_2);
            index += wall_link_type_2.length();
            link = linkToResource.substring(index);
        } else {
            throw new NotFoundException("Sorry");
        }
        return link;
    }

    public final String getResponse(final String postId)
            throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String json = "";
        try {
            HttpGet httpget = new HttpGet(
                    "http://api.vk.com/method/wall.getById?posts=" + postId
                            + "&extended=1&");

            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

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
            json = httpclient.execute(httpget, responseHandler);
        } finally {
            httpclient.close();
        }
        return json;
    }

    public final Record parseResponse(final String linkToResource)
            throws IOException, NullPointerException {

        byte[] jsonData = getResponse(parseLink(linkToResource))
                .getBytes(Charset.forName("UTF-8"));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode response = rootNode.get("response");

        if (response == null) {
            throw new NullPointerException("Response is null");
        } else if (response.get("wall").size() == 0) {
            throw new NullPointerException("Post not contains wall[]!");
        } else if (response.get("profiles").size() == 0
                && response.get("groups").size() == 0) {
            throw new NullPointerException("Post not contains groups[]!");
        }
        if (response.get("groups").size() != 0) {
            return parseByGroupType(linkToResource, rootNode, jsonData);

        } else {
            return parseByUserType(linkToResource, rootNode, jsonData);
        }
    }

    public final Record parseByUserType(final String linkToResource,
            final JsonNode rootNode, byte[] jsonData)
            throws JsonProcessingException, IOException {

        JsonNode response = rootNode.get("response");
        JsonNode wall = response.get("wall");
        JsonNode profiles = response.get("profiles");
        JsonNode firstUserName = profiles.findValue("first_name");
        JsonNode lastUserName = profiles.findValue("last_name");
        JsonNode userProfileUrl = profiles.findValue("screen_name");
        JsonNode userPhotoUrl = profiles.findValue("photo_medium_rec");
        JsonNode message = wall.findValue("text");

        return new Record(1, firstUserName.asText() + " "
                + lastUserName.asText(), linkToResource, "http://vk.com/"
                + userProfileUrl.asText(), userPhotoUrl.asText(),
                message.asText(), getRecordsPhotoUrl(jsonData));
    }

    public final Record parseByGroupType(final String linkToResource,
            final JsonNode rootNode, final byte[] jsonData)
            throws JsonProcessingException, IOException {

        JsonNode response = rootNode.get("response");
        JsonNode wall = response.get("wall");
        JsonNode groups = response.get("groups");
        JsonNode groupName = groups.findValue("name");
        JsonNode groupProfileUrl = groups.findValue("gid");
        JsonNode userPhotoUrl = groups.findValue("photo_medium");
        JsonNode message = wall.findValue("text");

        return new Record(1, groupName.asText(), linkToResource,
                "http://vk.com/public" + groupProfileUrl.asText(),
                userPhotoUrl.asText(), message.asText(),
                getRecordsPhotoUrl(jsonData));
    }

    private final String getRecordsPhotoUrl(final byte[] jsonData)
            throws JsonParseException, IOException {

        JsonFactory jfactory = new JsonFactory();

        JsonParser jParser = jfactory.createParser(jsonData);

        while (jParser.nextToken() != JsonToken.END_ARRAY) {

            if ("attachments".equals(jParser.getCurrentName())) {
                while (jParser.nextToken() != JsonToken.END_ARRAY) {
                    if ("src_big".equals(jParser.getCurrentName())) {
                        return jParser.nextTextValue();
                    }
                }
            }
            jParser.nextToken();
        }
        return "";
    }
}
