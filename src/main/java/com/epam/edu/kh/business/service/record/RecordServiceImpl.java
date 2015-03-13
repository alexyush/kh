package com.epam.edu.kh.business.service.record;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.epam.edu.kh.business.dao.record.RecordDao;
import com.epam.edu.kh.business.dao.tag.TagDao;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;

@Component("recordServiceImpl")
public class RecordServiceImpl implements RecordService {

    @Autowired
    @Qualifier("recordDaoImpl")
    private RecordDao recordDao;

    @Autowired
    @Qualifier("tagDaoImpl")
    private TagDao tagDao;

    public RecordServiceImpl() {

    }

    public final List<Record> getTopRecords() {
        List<Record> response = recordDao.getTopRecords(20);
        return response;
    }

    public final void insertRecord(final Record record)
            throws IllegalArgumentException, IOException {

        record.setTags(addTagsToRecord(record.getTags(),
                tagDao.getTagsFromMessage(record.getMessage())));
        record.setMessage(cutString(160, record.getMessage()));
        recordDao.saveRecord(record);

    }

    private String cutString(int i, String str) {
        String newString;
        if (i < str.length()) {
            newString = str.substring(0, i);
        } else {
            return str;
        }
        newString = newString.concat("...");
        return newString;
    }

    private Set<Tag> addTagsToRecord(Set<Tag> recordsTags, Set<Tag> tags) {

        for (Tag tag : tags) {
            recordsTags.add(tag);
        }
        return recordsTags;
    }

    public final Set<Record> getRecordsByTagsName(Names tagsNames) {

        Set<Record> records = new HashSet<Record>();
        for (String tagsName : tagsNames.getNames()) {
            records.addAll(tagDao.getTagByName(tagsName).getRecords());
        }

        return records;
    }

    public final List<Tag> getTopTags() {

        List<Tag> tags = tagDao.getAllTags();
        qSort(tags, 0, tags.size() - 1);
        Collections.reverse(tags);
        if (tags.size() <= 20) {
            return tags;
        } else {
            return tags.subList(0, 20);
        }

    }

    private void qSort(List<Tag> tags, int start, int end) {

        if (tags.size() != 0) {
            int i = start;
            int j = end;
            int x = tags.get((i + j) / 2).getRecords().size();
            do {

                while (tags.get(i).getRecords().size() < x) {
                    ++i;
                }
                while (tags.get(j).getRecords().size() > x) {
                    --j;
                }
                if (i <= j) {
                    Tag temp = tags.get(i);
                    tags.set(i, tags.get(j));
                    tags.set(j, temp);
                    i++;
                    j--;
                }
            } while (i <= j);

            if (start < j) {
                qSort(tags, start, j);
            }
            if (i < end) {
                qSort(tags, i, end);
            }
        }
    }

    public final Long getLastDateOfCreate() {

        return recordDao.getLastDateOfCreate();
    }

    public final List<Record> getAllRecords() {

        return recordDao.getAllRecords();
    }

    public final void delete(long id) {

        recordDao.delete(id);
    }

    public final void updateRecord(Record rec) {
        recordDao.updateRecord(rec);
    }

}
