package com.epam.edu.kh.business.service;

import java.io.IOException; 

import com.epam.edu.kh.business.entity.Record;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RecordService {

    public void insertRecord(String links) throws JsonProcessingException,
            IOException, Exception;

    public void checkDataBySchedule() throws IOException, Exception;

    public void compareWithOriginalData(Record rec) throws IOException, Exception;
}
