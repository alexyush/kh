package com.epam.edu.kh.business.dao;

import java.util.List;
 
import com.epam.edu.kh.business.entity.Record;

public interface RecordDao {
	void saveRecord(Record record); 
	Record getRecord(long id);
	List<Record> getListRecords();
	void delete(long id);
	List<Record> getAllRecords();
	void updateRecord(Record rec);
}
