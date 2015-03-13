package com.epam.edu.kh.business.scanner;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.service.record.RecordService;

@Component("socialService")
public class SocialService {

    @Autowired
    @Qualifier("socialReaderVk")
    private SocialReader socialReader;

    @Autowired
    @Qualifier("recordServiceImpl")
    private RecordService recordService;

    public final void searchForNewRecords() {

        try {
            Long startTime = recordService.getLastDateOfCreate() + 1;
            socialReader.getAndSaveNewRecords("ДобраеСэрца",
                    startTime.toString());

        } catch (NullPointerException e) {

            socialReader.getAndSaveNewRecords("ДобраеСэрца", "");
        }

    }

    public final void updateRecords() {

        if (recordService.getAllRecords().size() != 0) {
            Iterator<Record> recIt = recordService.getAllRecords().iterator();
            Record currentrec;
            while (recIt.hasNext()) {
                currentrec = recIt.next();
                try {
                    recordService.updateRecord(socialReader
                            .getNewestDataForUpdate(currentrec));

                } catch (Exception ex) {
                    System.out.println("Error:" + ex);
                    recordService.delete(currentrec.getId());
                }
            }
        }
    }
}
