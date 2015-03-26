package com.epam.edu.kh.business.dao.tag;

import java.util.List;
import java.util.Set;

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;

public interface TagDao {
    void save(Tag tag);

    Tag get(long id);

    Tag insert(String tagName);

    Tag getByName(String tagName);

    Set<Tag> getFromMessage(String recordMessage);

    List<Tag> getTopTags(Integer topTags);
    
    List<Record> getRecordsByTagNames(List<String> tagNames);
}
