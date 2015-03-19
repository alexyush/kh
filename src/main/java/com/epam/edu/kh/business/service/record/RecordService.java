package com.epam.edu.kh.business.service.record;

import java.util.List;
import java.util.Set;

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;
import com.epam.edu.kh.business.entity.TagNames;

public interface RecordService {

    void insertRecord(Record record);

    void deleteRecord(long id);

    void updateRecord(Record record);

    Long getDateOfLastInsertedRecord(String source);

    Record getRecord(Long id);

    List<Tag> getTopTags();

    List<Record> getTopRecords();

    List<Record> getAllRecords();

    Set<Record> getRecordsByTagNames(TagNames tagsNames);

    void saveBatch(List<Record> newRecords);
}
