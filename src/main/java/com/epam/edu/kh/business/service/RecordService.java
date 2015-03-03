package com.epam.edu.kh.business.service;

import java.io.IOException;
import java.util.List;

import com.epam.edu.kh.business.entity.Record;
import com.fasterxml.jackson.core.JsonProcessingException;
 
public interface RecordService {

	public void insertRecord(String links) throws JsonProcessingException, IOException, Exception;
	public void recordsController() throws IOException,Exception;
	public void checkRecord(Record rec) throws IOException,Exception;
}
