package com.epam.edu.kh.business.dao.record;

import java.io.IOException;
import java.util.List;

import com.epam.edu.kh.business.entity.Record;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RecordDao {
    void saveRecord(Record record);

    List<Record> getTopRecords(int count);

    void delete(long id);

    List<Record> getAllRecords();

    void updateRecord(Record rec);

    Long getLastDateOfCreate();

    void insertRecord(Record record) throws JsonProcessingException,
            IOException;
}
