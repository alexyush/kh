package com.epam.edu.kh.business.service.record;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component; 

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

    public final List<Record> getTopRecords() {
        List<Record> response = recordDao.getTop(Integer.parseInt(topRecords));
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

    public void saveBatch(List<Record> newRecords) {
        for(Record record:newRecords) {
            recordDao.insert(record);
        }
    }
    public final Set<Record> getRecordsByTagNames(TagNames tagsNames) {
        Set<Record> records = new HashSet<Record>();
        for (String tagsName : tagsNames.getNames()) {
            records.addAll(tagDao.getByName(tagsName).getRecords());
        }
        return records;
    }

    public final List<Tag> getTopTags() {  
        return tagDao.getTopTags(Integer.valueOf(topTags));
    }

}
