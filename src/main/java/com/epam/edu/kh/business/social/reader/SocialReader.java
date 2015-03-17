package com.epam.edu.kh.business.social.reader;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.epam.edu.kh.business.entity.Record;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface SocialReader {

    List<Record> getNewRecordsByTag(String tag) throws ClientProtocolException,
            IOException; 

    Record updateRecord(Long id) throws JsonProcessingException,
            ClientProtocolException, IOException;

}
