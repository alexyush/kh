package com.epam.edu.kh.business.service.record;

import java.io.IOException;
import java.util.Set;

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RecordService {

    void insertRecord(String links) throws JsonProcessingException,
            IOException;

    void scheduledUpdate();

    void updatePropertiesOfRecord(Record rec);

    Set<Tag> addTagsToRecord(Set<Tag> recordTags, Set<Tag> tags);
}
