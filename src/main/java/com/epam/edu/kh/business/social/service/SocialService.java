package com.epam.edu.kh.business.social.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.service.record.RecordService;
import com.epam.edu.kh.business.social.reader.SocialReader;
import com.fasterxml.jackson.core.JsonProcessingException;

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
        try {
            newRecords = socialReader.getNewRecordsByTag("ДобраеСэрца");
            Iterator<Record> newRecordsIt = newRecords.iterator();
            while (newRecordsIt.hasNext()) {
                recordService.insertRecord(newRecordsIt.next());
            }
        } catch (ClientProtocolException e1) {
            System.out.println("in getAndSaveNewRecordsByTag:" + e1);
        } catch (IOException e1) {
            System.out.println("in getAndSaveNewRecordsByTag:" + e1);
        }

    }

    public final void updatesAllRecords() {

        Iterator<Record> recIt = recordService.getAllRecords().iterator();
        Record currentrec;
        while (recIt.hasNext()) {
            currentrec = recIt.next();
            try {
                currentrec = socialReader.updateRecord(currentrec.getId());
                recordService.updateRecord(currentrec);
            } catch (JsonProcessingException e) {
                System.out.println(e);
            } catch (ClientProtocolException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }
}
