package com.epam.edu.kh.business.service.tag;

import java.util.List;
import java.util.Set;

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;

public interface TagService {

    Set<Tag> getTagsFromMessage(String message);

    Tag insertTag(String tagName);

    Set<Record> getRecordsByTagName(Set<String> tags);

    List<Tag> getTopTags();
}
