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
import com.epam.edu.kh.business.entity.TagNames;

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
        List<Record> response = recordDao.getTop(20);
        return response;
    }

    public final Long getDateOfLastInsertedRecord() {

        return recordDao.getDateOfLastInsertedRecord();
    }

    public final List<Record> getAllRecords() {

        return recordDao.getAll();
    }

    public final void deleteRecord(long id) {

        recordDao.delete(id);
    }

    public final void updateRecord(Record rec) {
        recordDao.update(rec);
    }

    public final Record getRecord(Long id) {
        return recordDao.get(id);
    }

    public final void insertRecord(final Record record)
            throws IllegalArgumentException, IOException {

        record.setTags(addTagsToRecord(record.getTags(),
                tagDao.getFromMessage(record.getMessage())));
        record.setMessage(cutString(160, record.getMessage()));
        recordDao.save(record);

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

    public final Set<Record> getRecordsByTagNames(TagNames tagsNames) {

        Set<Record> records = new HashSet<Record>();
        for (String tagsName : tagsNames.getNames()) {
            records.addAll(tagDao.getByName(tagsName).getRecords());
        }

        return records;
    }

    public final List<Tag> getTopTags() {

        List<Tag> tags = tagDao.getAll();
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
}
