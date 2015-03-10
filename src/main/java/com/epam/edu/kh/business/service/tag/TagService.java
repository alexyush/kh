package com.epam.edu.kh.business.service.tag;

import java.util.List;
import java.util.Set;

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;

public interface TagService {

    public Set<Tag> getTagsFromMessage(String message);

    public Tag insertTag(String tagName);

    public Set<Record> getRecordsByTagName(Set<String> tags);

    public List<Tag> getTopTags();
}
