package com.epam.edu.kh.business.service.record;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epam.edu.kh.business.dao.record.RecordDao;
import com.epam.edu.kh.business.dao.tag.TagDao;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;
import com.epam.edu.kh.business.entity.TagNames;

@Component("recordServiceImpl")
public class RecordServiceImpl implements RecordService {

    @Value("${topTags}")
    private String topTags;

    @Value("${topRecords}")
    private String topRecords;

    @Autowired
    @Qualifier("recordDaoImpl")
    private RecordDao recordDao;

    @Autowired
    @Qualifier("tagDaoImpl")
    private TagDao tagDao;

    @Transactional(readOnly = true)
    public final List<Record> getTopRecords() {
        List<Record> response = recordDao.getTopRecords(Integer.parseInt(topRecords));
        return response;
    }

    @Transactional(readOnly = true)
    public final Long getDateOfLastInsertedRecord(String source) {
        return recordDao.getDateOfLastInsertedRecord(source);
    }

    @Transactional
    public final void deleteRecord(long id) {

        recordDao.delete(id);
    }

    @Transactional
    public final void updateRecord(Record record) {
        recordDao.update(record);
    }

    @Transactional(readOnly = true)
    public final Record getRecord(Long id) {
        return recordDao.get(id);
    }

    @Transactional
    public void saveBatch(List<Record> newRecords) {
        for (Record record : newRecords) {
            recordDao.insert(record);
        }
    }

    @Transactional(readOnly = true)
    public final Set<Record> getRecordsByTagNames(TagNames tagNames) {
        Set<Record> records = new HashSet<Record>();
        records.addAll(tagDao.getRecordsByTagNames(tagNames.getNames()));
        return records;
    }

    @Transactional(readOnly = true)
    public final List<Tag> getTopTags() {
        return tagDao.getTopTags(Integer.valueOf(topTags));
    }
}
