package com.epam.edu.kh.business.dao;

import java.util.List;
 
import com.epam.edu.kh.business.entity.Record;

public interface RecordDao {
	void save(Record record); 
	Record getPet(long id);
	List<Record> getListPets();
	void delete(long id);
}
