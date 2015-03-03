package com.epam.edu.kh.business.service;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.epam.edu.kh.business.dao.RecordDao;
import com.epam.edu.kh.business.entity.Record;

public class RecordServiceImpl implements RecordService {

	
	@Autowired
	private JsonScanner jsonScanner;
	

	@Autowired
	private RecordDao recordDao;
	
	public void insertRecord(String linkToResource) throws Exception {
	
		Record record = jsonScanner.parseJsonFromVkServer(linkToResource);
		record.setMessage(cutString(160,record.getMessage()));
		recordDao.saveRecord(record);
		
	} 

	//@Scheduled(fixedDelay = 15000)//время в миллисекундах
	public void recordsController() throws Exception {

		Iterator<Record> iter = recordDao.getAllRecords().iterator();
		while (iter.hasNext()) {
			checkRecord(iter.next());
		}
	}

	public void checkRecord(Record rec) throws Exception {

		try {

			Record other = jsonScanner
					.parseJsonFromVkServer(rec.getSourceUrl());

			if (rec.equals(other)) {

				rec.setMessage(other.getMessage());
				rec.setRecordPhotoUrl(other.getRecordPhotoUrl());
				rec.setUserName(other.getUserName());
				rec.setUserPhotoUrl(other.getUserPhotoUrl());
				rec.setUserProfileUrl(other.getUserProfileUrl());
				recordDao.updateRecord(rec);

			}

		} catch (Exception ex) {
			recordDao.delete(rec.getId());
		}
	}

	public String cutString(int i, String str) {
		String newString;
		if (i < str.length())
			newString = str.substring(0, i);
		else
			return str;
		newString = newString.concat("...");
		return newString;
	}
}
