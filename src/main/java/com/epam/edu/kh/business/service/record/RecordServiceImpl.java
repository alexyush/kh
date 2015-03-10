package com.epam.edu.kh.business.service.record;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.epam.edu.kh.business.dao.record.RecordDao;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;
import com.epam.edu.kh.business.scanner.SocialScanner;
import com.epam.edu.kh.business.service.tag.TagService;

@Component("recordServiceImpl")
public class RecordServiceImpl implements RecordService {

    @Autowired
    @Qualifier("socialScannerVk")
    private SocialScanner socialScannerOfVk;

    @Autowired
    @Qualifier("recordDaoImpl")
    private RecordDao recordDao;

    @Autowired
    @Qualifier("tagServiceImpl")
    private TagService tagService;

    public RecordServiceImpl() {

    }

    public final void insertRecord(final String linkToResource)
            throws IllegalArgumentException, IOException {

        Record record = socialScannerOfVk.parseResponse(linkToResource);
        record.setTags(addTagsToRecord(record.getTags(),
                tagService.getTagsFromMessage(record.getMessage())));
        record.setMessage(cutString(160, record.getMessage()));
        for (Tag tag : record.getTags())
            System.out.println(tag.getName());
        recordDao.saveRecord(record);

    }

    @Scheduled(fixedDelay = 300000)
    public final void scheduledUpdate() {

        Iterator<Record> iter = recordDao.getAllRecords().iterator();
        while (iter.hasNext()) {
            updatePropertiesOfRecord(iter.next());
        }
    }

    public final void updatePropertiesOfRecord(final Record rec) {

        try {

            Record other = socialScannerOfVk.parseResponse(rec.getSourceUrl());

            rec.setMessage(other.getMessage());
            rec.setRecordPhotoUrl(other.getRecordPhotoUrl());
            rec.setUserName(other.getUserName());
            rec.setUserPhotoUrl(other.getUserPhotoUrl());
            rec.setUserProfileUrl(other.getUserProfileUrl());
            rec.setTags(addTagsToRecord(other.getTags(),
                    tagService.getTagsFromMessage(other.getMessage())));
            recordDao.updateRecord(rec);

        } catch (Exception ex) {
            System.out.println(ex);
            recordDao.delete(rec.getId());
        }
    }

    public final String cutString(int i, String str) {
        String newString;
        if (i < str.length())
            newString = str.substring(0, i);
        else
            return str;
        newString = newString.concat("...");
        return newString;
    }

    public final Set<Tag> addTagsToRecord(Set<Tag> recordsTags, Set<Tag> tags) {

        for (Tag tag : tags) {
            recordsTags.add(tag);
        }

        return recordsTags;

    }
}
