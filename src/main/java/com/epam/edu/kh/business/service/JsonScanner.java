package com.epam.edu.kh.business.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;

import com.epam.edu.kh.business.entity.Record;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonScanner {

	public JsonScanner() {
	}
	public String parseLinkVk(String linkToResource) throws NotFoundException {

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
		}
		else {
			throw new NotFoundException("Sorry");
		}
		return link;
	}
	public String getJsonFromVkServer(String postId) throws IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		String json = "";
		try {
			HttpGet httpget = new HttpGet(
					"http://api.vk.com/method/wall.getById?posts=" + postId
							+ "&extended=1&");
			System.out.println("Executing request " + httpget.getRequestLine());
			// Create a custom response handler
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

	public Record parseJsonFromVkServer(String linkToResource)
			throws IllegalArgumentException, IOException {

		byte[] jsonData = getJsonFromVkServer(parseLinkVk(linkToResource))
				.getBytes(Charset.forName("UTF-8"));
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonData);
		JsonNode response = rootNode.get("response");

		if (response.get("wall").size() == 0) {
			throw new IllegalArgumentException("Could not find data in post!");
		}
		else if (response.get("profiles").size() == 0
				&& response.get("groups").size() == 0) {
			throw new IllegalArgumentException("Could not find data in post!");
		}
		if (response.get("groups").size() != 0) {
			return parseJsonByGroupType(linkToResource, rootNode);

		} else {
			return parseJsonByUserType(linkToResource, rootNode);
		}
	}

	public Record parseJsonByUserType(String linkToResource, JsonNode rootNode)
			throws JsonProcessingException, IOException {

		JsonNode response = rootNode.get("response");
		JsonNode wall = response.get("wall");
		JsonNode profiles = response.get("profiles"); 
		JsonNode firstUserName = profiles.findValue("first_name");
		JsonNode lastUserName = profiles.findValue("last_name");
		JsonNode userProfileUrl = profiles.findValue("screen_name");
		JsonNode userPhotoUrl = profiles.findValue("photo_medium_rec");
		JsonNode message = wall.findValue("text");
		// JsonNode recordPhotoUrl = profiles.findValue("photo_medium_rec");
		return new Record(1, firstUserName.asText() + " "
				+ lastUserName.asText(), linkToResource, "http://vk.com/"
				+ userProfileUrl.asText(), userPhotoUrl.asText(),
				message.asText(),
				"http://cs625116.vk.me/v625116006/107e9/JphVBmmAbYg.jpg");
	}

	public Record parseJsonByGroupType(String linkToResource, JsonNode rootNode)
			throws JsonProcessingException, IOException {

		JsonNode response = rootNode.get("response");
		JsonNode wall = response.get("wall");
		JsonNode groups = response.get("groups");
		JsonNode groupName = groups.findValue("name");
		JsonNode groupProfileUrl = groups.findValue("gid");
		JsonNode userPhotoUrl = groups.findValue("photo_medium");
		JsonNode message = wall.findValue("text");
		// JsonNode recordPhotoUrl = groups.findValue("photo_medium_rec");

		return new Record(1, groupName.asText(), linkToResource,
				"http://vk.com/public" + groupProfileUrl.asText(),
				userPhotoUrl.asText(), message.asText(),
				"http://cs625222.vk.me/v625222115/a456/xCdt6UopAyY.jpg");
	}

	public void parseJsonForTesting(String linkToResource)
			throws JsonProcessingException, IOException {

		byte[] jsonData = getJsonFromVkServer(parseLinkVk(linkToResource))
				.getBytes(Charset.forName("UTF-8"));

		JsonFactory jfactory = new JsonFactory();
		 
        /*** READ JSON DATA FROM FILE ***/
        JsonParser jParser = jfactory
                .createParser(jsonData);

        // LOOP UNTIL WE READ END OF JSON DATA, INDICATED BY }
        while (jParser.nextToken() != JsonToken.END_OBJECT) {

            String fieldname = jParser.getCurrentName();
            
            //System.out.println(jParser.getText());
          	if ("attachment".equals(fieldname)) 
          	{
               	
                jParser.nextToken();
                System.out.println(jParser.getText());
                
                while (jParser.nextToken() != JsonToken.END_ARRAY) { 
                	
                	jParser.nextToken();
                	System.out.println(jParser.getText());
                	//if("src_big".equals(jParser.getCurrentName()))
                		//System.out.println(jParser.getCurrentName()+":"+jParser.getText()); 
                }
            }
        }
		
		
		
		/*ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonData);

		JsonNode response = rootNode.get("response");
		JsonNode wall = response.get("wall");

		Iterator<JsonNode> wallList = wall.withArray("attachments").elements();

		while (wallList.hasNext()) {
			
		
			//Iterator<String> aat = wallList.next().fieldNames();
			//attachment
			//while (aat.hasNext()) {
				System.out.println(wallList.next());
			//}
		}*/
	}
}