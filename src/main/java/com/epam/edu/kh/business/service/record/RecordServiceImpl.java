package com.epam.edu.kh.business.service.record;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.epam.edu.kh.business.domain.Record;
import com.epam.edu.kh.business.domain.Tag;
import com.epam.edu.kh.business.domain.TagNames;
import com.epam.edu.kh.business.repository.RecordRepository;
import com.epam.edu.kh.business.repository.TagRepository;
import com.google.common.collect.Lists;

@Component("recordServiceImpl")
public class RecordServiceImpl implements RecordService {

    @Value("${topTags}")
    private String topTags;

    @Value("${topRecords}")
    private String topRecords;

    @Autowired
    private RecordRepository recordRepo;

    @Autowired
    private TagRepository tagRepo;

    @Transactional(readOnly = true)
    public final List<Record> getTopRecords() { 
        List<Record> listOfRecords = Lists.newArrayList(recordRepo.getTopRecords(Integer.parseInt(topRecords))); 
        return listOfRecords;
    }

    @Transactional(readOnly = true)
    public final Long getDateOfLastInsertedRecord(String source) {
        return recordRepo.getDateOfLastInsertedRecord(source);
    }

    @Transactional
    public final void deleteRecord(Long id) {
        recordRepo.delete(id);
    }

    @Transactional
    public void saveBatch(List<Record> newRecords) {
        for (Record record : newRecords) {
            insertRecord(record);
        }
    }

    @Transactional(readOnly = true)
    public final List<Record> getRecordsByTagNames(TagNames tagNames) {
        List<Record> listOfRecords = Lists.newArrayList(tagRepo.getRecordsByTagNames(tagNames.getNames()));
        return listOfRecords;
    }

    @Transactional(readOnly = true)
    public final List<Tag> getTopTags() {
        List<Tag> listOfTags = Lists.newArrayList(tagRepo.getTopTags(Integer.parseInt(topTags)));
        return listOfTags;
    }

    private void insertRecord(final Record record) {
        record.getTags().addAll(getFromMessage(record.getMessage()));
        recordRepo.save(record);
    }

    private Set<Tag> getFromMessage(final String recordMessage) {

        String messageOfRecord = recordMessage;
        String[] hashTags = messageOfRecord.split(" ");
        Set<String> uniqueHashTags = new HashSet<String>();

        for (String tag : hashTags) {
            if (tag.startsWith("#")) {
                uniqueHashTags.add(tag.substring(1));
            }
        }
        Set<Tag> tags = new HashSet<Tag>();
        for (String tag : uniqueHashTags) {
            tags.add(insertTag(tag));
        }
        return tags;
    }

    private Tag insertTag(final String tagName) {
        if (tagRepo.findByTagName(tagName) != null) {
            return tagRepo.findByTagName(tagName);
        } else {
            Tag tag = new Tag(tagName);
            tagRepo.save(tag);
            return tag;
        }
    }
}
