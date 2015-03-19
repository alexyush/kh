package com.epam.edu.kh.business.service.record;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    public final List<Record> getTopRecords() {
        List<Record> response = recordDao.getTop(20);
        Collections.reverse(response);
        return response;
    }

    public final Long getDateOfLastInsertedRecord(String source) {

        return recordDao.getDateOfLastInsertedRecord(source);
    }

    public final List<Record> getAllRecords() {

        return recordDao.getAll();
    }

    public final void deleteRecord(long id) {

        recordDao.delete(id);
    }

    public final void updateRecord(Record record) {
        recordDao.update(record);
    }

    public final Record getRecord(Long id) {
        return recordDao.get(id);
    }

    @Transactional
    public final void insertRecord(final Record record) {

        record.getTags().addAll(tagDao.getFromMessage(record.getMessage()));
        record.setMessage(cutString(250, record.getMessage()));
        recordDao.save(record);

    }

    @Transactional
    public void saveBatch(List<Record> newRecords) {

        for (Record newRecord : newRecords) {
            insertRecord(newRecord);
        }
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
