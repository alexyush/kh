package com.epam.edu.kh.business.dao.record;

import java.util.List;
import com.epam.edu.kh.business.entity.Record;

public interface RecordDao {

    void save(Record record);

    void update(Record rec);

    void delete(Long id);

    Long getDateOfLastInsertedRecord();

    Record get(Long id);

    List<Record> getTop(int count);

    List<Record> getAll();

}
