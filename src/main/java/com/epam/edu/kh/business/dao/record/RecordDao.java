package com.epam.edu.kh.business.dao.record;

import java.util.List;
import com.epam.edu.kh.business.entity.Record;

public interface RecordDao {
    void saveRecord(Record record);

    List<Record> getTopRecords();

    void delete(long id);

    List<Record> getAllRecords();

    void updateRecord(Record rec);
}
