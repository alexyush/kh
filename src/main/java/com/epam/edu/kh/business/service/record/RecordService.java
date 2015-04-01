package com.epam.edu.kh.business.service.record;

import java.util.List;
import com.epam.edu.kh.business.domain.Record;
import com.epam.edu.kh.business.domain.Tag;
import com.epam.edu.kh.business.domain.TagNames;

public interface RecordService { 

    void deleteRecord(Long id);

    Long getDateOfLastInsertedRecord(String source);

    Record getRecord(Long id);

    List<Tag> getTopTags();

    List<Record> getTopRecords();

    List<Record> getRecordsByTagNames(TagNames tagsNames);

    void saveBatch(List<Record> newRecords); 

}
