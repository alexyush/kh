package com.epam.edu.kh.business.service.record;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.epam.edu.kh.business.dao.record.RecordDao;
import com.epam.edu.kh.business.entity.Record;

@Component("recordServiceImpl")
public class RecordServiceImpl implements RecordService {

    @Autowired
    @Qualifier("recordDaoImpl")
    private RecordDao recordDao;

    public RecordServiceImpl() {

    }

    public final List<Record> getTopRecords() {
        List<Record> response = recordDao.getTopRecords(20);
        return response;
    }
}
