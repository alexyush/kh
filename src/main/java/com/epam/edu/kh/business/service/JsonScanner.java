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

import com.epam.edu.kh.business.entity.Record;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonScanner {

	
	public JsonScanner(){
	} 
	public String parseLinkVk(String linkToResource){
		 
		String link = "";
		if(linkToResource.contains("?w=wall"))
		{
			  
			int index = linkToResource.lastIndexOf("?w=wall");
			index += 7;
			link  = linkToResource.substring(index); 
			
		}
		else if(linkToResource.contains("http://vk.com/wall"))
		{
			int index = linkToResource.lastIndexOf("http://vk.com/wall");
			index += 18;
			link  = linkToResource.substring(index);
		}
		System.out.println(link);
		return link;
	} 
	public String getJsonFromVkServer(String postId) throws IOException{
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String json = "";
		try 
		{
            
        	HttpGet httpget = new HttpGet("http://api.vk.com/method/wall.getById?posts="+postId+"&extended=1&");	
            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() 
            {

		            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException 
		            {
			            int status = response.getStatusLine().getStatusCode();
			            if (status >= 200 && status < 300) 
			            {
			                 HttpEntity entity = response.getEntity();
			                 return entity != null ? EntityUtils.toString(entity) : null;
			            } 
			            else 
			            {
			                 throw new ClientProtocolException("Unexpected response status: " + status);
			            }
		            }

            };
            json = httpclient.execute(httpget, responseHandler);   
        }
		finally 
		{
            httpclient.close();
        }
		return json;
	}
	
	public Record parseJsonFromVkServer(String linkToResource) throws IOException{
		
		 

		byte[] jsonData = getJsonFromVkServer(parseLinkVk(linkToResource)).getBytes(Charset.forName("UTF-8"));  
		
		ObjectMapper objectMapper = new ObjectMapper();  
		JsonNode rootNode = objectMapper.readTree(jsonData);
	 
		JsonNode response = rootNode.get("response"); 
		//JsonNode profiles = response.get("profiles"); 
		JsonNode groups = response.get("groups");
		 
		if(groups.has(0))
		{
			
			System.out.println("Hey,it's group");
			return parseJsonByGroupType(linkToResource,rootNode);
			
		}
		else/*(profiles.size()!=0)*/
		{
			System.out.println("Hey,it's profile");
			return parseJsonByUserType(linkToResource,rootNode);
		}

	}
	public Record parseJsonByUserType(String linkToResource,JsonNode rootNode) throws JsonProcessingException, IOException{
		
	//	byte[] jsonData = getJsonFromVkServer(parseLinkVk(linkToResource)).getBytes(Charset.forName("UTF-8"));  
		 
		//ObjectMapper objectMapper = new ObjectMapper();
		//JsonNode rootNode = objectMapper.readTree(jsonData);
	 
		JsonNode response = rootNode.get("response");
		JsonNode wall = response.get("wall");
		JsonNode profiles = response.get("profiles"); 
		
		//JsonNode attachment = wall.get("attachment"); 
		
		JsonNode firstUserName = profiles.findValue("first_name");
		JsonNode lastUserName = profiles.findValue("last_name");
		JsonNode userProfileUrl = profiles.findValue("screen_name");
		JsonNode userPhotoUrl = profiles.findValue("photo_medium_rec");
		JsonNode message = wall.findValue("text");
		//JsonNode recordPhotoUrl = profiles.findValue("photo_medium_rec");
	
		return new Record(1,
				firstUserName.asText() + " " + lastUserName.asText(),
				linkToResource,
				"http://vk.com/"+userProfileUrl.asText(),
				userPhotoUrl.asText(),
				message.asText(),
				"http://cs625116.vk.me/v625116006/107e9/JphVBmmAbYg.jpg");
	}
	public Record parseJsonByGroupType(String linkToResource,JsonNode rootNode) throws JsonProcessingException, IOException{
		
		//byte[] jsonData = getJsonFromVkServer(parseLinkVk(linkToResource)).getBytes(Charset.forName("UTF-8"));  
		 
		//ObjectMapper objectMapper = new ObjectMapper();
		//JsonNode rootNode = objectMapper.readTree(jsonData);
	 
		JsonNode response = rootNode.get("response");
		JsonNode wall = response.get("wall");
		JsonNode groups = response.get("groups"); 
		
		//JsonNode attachment = wall.get("attachment"); 
		
		JsonNode groupName = groups.findValue("name"); 
		JsonNode groupProfileUrl = groups.findValue("gid");
		JsonNode userPhotoUrl = groups.findValue("photo_medium");
		JsonNode message = wall.findValue("text");
		//JsonNode recordPhotoUrl = groups.findValue("photo_medium_rec");
		
		return new Record(1,
				groupName.asText(),
				linkToResource,
				"http://vk.com/public"+groupProfileUrl.asText(),
				userPhotoUrl.asText(),
				message.asText(),
				"http://cs625222.vk.me/v625222115/a456/xCdt6UopAyY.jpg");
	}
	
	public void parseJsonForTesting(String linkToResource) throws JsonProcessingException, IOException{
		
		byte[] jsonData = getJsonFromVkServer(parseLinkVk(linkToResource)).getBytes(Charset.forName("UTF-8"));  
		 
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonData);
	 
		JsonNode response = rootNode.get("response");
		JsonNode wall = response.get("wall");
		JsonNode groups = response.get("groups"); 
		JsonNode attachment = wall.get("attachment");
 
		System.out.println("*************************************");
		
		Iterator<JsonNode> responseList = response.elements(); 
		while(responseList.hasNext()){
			
			JsonNode res = responseList.next(); 
			System.out.println(res.toString()); 
			
		}
		Iterator<JsonNode> wallList = wall.elements(); 
		while(wallList.hasNext()){
			
			JsonNode wal = wallList.next(); 
			System.out.println(wal.toString()); 
			
		}
		Iterator<JsonNode> groupsList = groups.elements(); 
		while(groupsList.hasNext()){
			
			JsonNode gr = groupsList.next(); 
			System.out.println(gr.toString());
			
		}
		
		
		/*
		Iterator<JsonNode> attaList = attachment.elements(); 
		while(attaList.hasNext()){
			
			JsonNode gr = attaList.next(); 
			System.out.println(gr.toString());
			
		}*/
		
		
		
		
		System.out.println("*************************************");
		
	}
	
	
	
	
	
	
	
}