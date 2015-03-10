package com.epam.edu.kh.business.service.record;

import java.io.IOException;
import java.util.Set;

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RecordService {

    public void insertRecord(String links) throws JsonProcessingException,
            IOException;

    public void scheduledUpdate();

    public void updatePropertiesOfRecord(Record rec);

    public Set<Tag> addTagsToRecord(Set<Tag> recordTags, Set<Tag> tags);
}
