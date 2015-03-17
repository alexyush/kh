package com.epam.edu.kh.business.social.service;

import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.service.record.RecordService;
import com.epam.edu.kh.business.social.reader.SocialReader;

@Component("socialService")
public class SocialService {

    @Autowired
    @Qualifier("socialReaderVk")
    private SocialReader socialReader;

    @Autowired
    @Qualifier("recordServiceImpl")
    private RecordService recordService;

    public final void searchForNewRecords() {

        List<Record> newRecords;
        
            newRecords = socialReader.getNewRecordsByTag("ДобраеСэрца");
            Iterator<Record> newRecordsIt = newRecords.iterator();
            while (newRecordsIt.hasNext()) {
                    recordService.insertRecord(newRecordsIt.next());
            }


    }
}
