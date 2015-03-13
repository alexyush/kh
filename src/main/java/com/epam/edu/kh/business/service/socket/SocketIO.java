package com.epam.edu.kh.business.service.socket;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.epam.edu.kh.business.dao.record.RecordDao;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.scanner.SocialScanner;

@Component("socketIO")
public class SocketIO {

    @Autowired
    @Qualifier("recordDaoImpl")
    private RecordDao recordDao;

    @Autowired
    @Qualifier("socialScannerVk")
    private SocialScanner socialScanner;

    @Scheduled(fixedDelay = 100000)
    public final void searchForNewRecords() {

        try {
            Long startTime = recordDao.getLastDateOfCreate() + 1;
            socialScanner.getAndSaveNewRecords("ДобраеСэрца",
                    startTime.toString());

        } catch (NullPointerException e) {

            socialScanner.getAndSaveNewRecords("ДобраеСэрца", "");
        }

    }

    @Scheduled(fixedDelay = 200000)
    public final void updateRecords() {

        try {
            Iterator<Record> recIt = recordDao.getAllRecords().iterator();
            while (recIt.hasNext()) {
                recordDao.updateRecord(socialScanner
                        .getNewestDataForUpdate(recIt.next()));
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public SocketIO() {

    }
}
