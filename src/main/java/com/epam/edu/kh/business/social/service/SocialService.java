package com.epam.edu.kh.business.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.epam.edu.kh.business.social.scanner.SocialReader;

@Component("socialService")
public class SocialService {

    @Autowired
    @Qualifier("socialReaderVk")
    private SocialReader socialReader;

    public final void searchForNewRecords() {
        socialReader.getAndSaveNewRecordsByTag("ДобраеСэрца");
    }

    public final void updateRecords() {
        socialReader.updatesAllRecords();
    }
}
