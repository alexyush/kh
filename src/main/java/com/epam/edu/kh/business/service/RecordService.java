package com.epam.edu.kh.business.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
 
public interface RecordService {

	public void insertRecord(String links) throws JsonProcessingException, IOException;
	
}
