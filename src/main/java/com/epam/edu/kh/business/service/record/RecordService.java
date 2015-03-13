package com.epam.edu.kh.business.service.record;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RecordService {

    List<Record> getTopRecords();

    void insertRecord(Record record) throws JsonProcessingException,
            IOException;

    Set<Record> getRecordsByTagsName(Names tagsNames);

    List<Tag> getTopTags();

    Long getLastDateOfCreate();

    List<Record> getAllRecords();

    void delete(long id);

    void updateRecord(Record rec);
}
