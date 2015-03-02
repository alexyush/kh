package com.epam.edu.kh.business.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.edu.kh.business.dao.RecordDao;
import com.epam.edu.kh.business.entity.Record;
import com.fasterxml.jackson.core.JsonProcessingException;

public class RecordServiceImpl implements RecordService {

	
	@Autowired
	private JsonScanner jsonScanner;
	

	@Autowired
	private RecordDao recordDao;
	
	public void insertRecord(String linkToResource) throws JsonProcessingException, IOException {
	
		Record record = jsonScanner.parseJsonFromVkServer(linkToResource);
		record.setMessage(cutString(160,record.getMessage()));
		recordDao.save(record);
		
	}
	public String cutString(int i,String str){
		
		String newString;
		
		if(i<str.length())
			newString = str.substring(0,i);
		else
			return str;
		newString = newString.concat("...");
		return newString;
	}

}
