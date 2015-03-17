package com.epam.edu.kh.business.service.record;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;
import com.epam.edu.kh.business.entity.TagNames;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RecordService {

    void insertRecord(Record record) throws JsonProcessingException,
            IOException;

    void deleteRecord(long id);

    void updateRecord(Record rec);

    Long getDateOfLastInsertedRecord();

    Record getRecord(Long id);

    List<Tag> getTopTags();

    List<Record> getTopRecords();

    List<Record> getAllRecords();

    Set<Record> getRecordsByTagNames(TagNames tagsNames);

}
