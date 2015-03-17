package com.epam.edu.kh.business.scheduller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.epam.edu.kh.business.social.service.SocialService;

@Component("scheduledJob")
public class ScheduledJob {

    @Autowired
    private SocialService socialService;

    @Scheduled(fixedDelay = 50000)
    public final void getNewRecords() {
        socialService.searchForNewRecords();
    }

    @Scheduled(fixedDelay = 200000)
    public final void updateRecords() {
        socialService.updatesAllRecords();
    }

}
