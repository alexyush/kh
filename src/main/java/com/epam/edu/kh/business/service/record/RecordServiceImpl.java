package com.epam.edu.kh.business.service.record;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.epam.edu.kh.business.dao.record.RecordDao;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;
import com.epam.edu.kh.business.scanner.JsonScannerOfResponseVK;
import com.epam.edu.kh.business.service.tag.TagService;

public class RecordServiceImpl implements RecordService {

    @Autowired
    private JsonScannerOfResponseVK jsonScannerOfResponseVK;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private TagService tagService;

    public final void insertRecord(final String linkToResource)
            throws IllegalArgumentException, IOException {

        Record record = jsonScannerOfResponseVK
                .parseJsonOfResponse(linkToResource);
        record.setTags(addTagToRecord(record.getTags(),
                tagService.getTagsFromMessage(record.getMessage())));
        record.setMessage(cutString(160, record.getMessage()));
        for(Tag tag:record.getTags())
            System.out.println(tag.getName());
        recordDao.saveRecord(record);

    }

    @Scheduled(fixedDelay = 150000)
    // ����� � �������������
    public final void checkDataBySchedule() throws Exception {

        Iterator<Record> iter = recordDao.getAllRecords().iterator();
        while (iter.hasNext()) {
            compareWithOriginalData(iter.next());
        }
    }

    public final void compareWithOriginalData(final Record rec)
            throws Exception {

        try {

            Record other = jsonScannerOfResponseVK.parseJsonOfResponse(rec
                    .getSourceUrl());

            if (rec.equals(other)) {

                rec.setMessage(other.getMessage());
                rec.setRecordPhotoUrl(other.getRecordPhotoUrl());
                rec.setUserName(other.getUserName());
                rec.setUserPhotoUrl(other.getUserPhotoUrl());
                rec.setUserProfileUrl(other.getUserProfileUrl());
                recordDao.updateRecord(rec);

            }

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

    public RecordServiceImpl() {

    }

    public final Set<Tag> addTagToRecord(Set<Tag> recordsTags, Set<Tag> tags) {

        for (Tag tag : tags) {
            recordsTags.add(tag);
        }

        return recordsTags;

    }
}