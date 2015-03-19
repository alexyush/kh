package com.epam.edu.kh.business.social.reader;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.service.record.RecordService;

@Component
public class SocialReaderTwitter implements SocialReader {

    @Value("${tag}")
    private String tag;

    @Value("${consumerKey}")
    private String consumerKey;

    @Value("${consumerSecret}")
    private String consumerSecret;

    @Value("${accessToken}")
    private String accessToken;

    @Value("${accessTokenSecret}")
    private String accessTokenSecret;

    @Autowired
    @Qualifier("recordServiceImpl")
    private RecordService recordService;

    private static final Logger log = LoggerFactory.getLogger(SocialReaderTwitter.class);

    private String getDateOfLastInsertedRecord() {
        String startTime;
        Long dateOfLastInsertedRecord = recordService.getDateOfLastInsertedRecord("twitter");
        if (dateOfLastInsertedRecord != null) {
            startTime = String.valueOf(dateOfLastInsertedRecord + 1);
        } else {
            startTime = "";
        }
        return startTime;
    }

    public final List<Record> getNewRecords() {

        Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);

        List<Record> recordsFromTw = new ArrayList<Record>();
        SearchResults results = twitter.searchOperations().search(tag);
        String dateOfLastInsertedRecordOfTw = getDateOfLastInsertedRecord();

        for (Tweet tw : results.getTweets()) {

            if (!dateOfLastInsertedRecordOfTw.isEmpty()) {

                if (Long.valueOf(getDateOfLastInsertedRecord()) < Long.valueOf(tw.getCreatedAt().getTime()) / 1000L) {

                    recordsFromTw.add(getTweetData(tw));
                }
            } else {
                recordsFromTw.add(getTweetData(tw));
            }
        }
        log.info("Date of last inserted record of tw:" + dateOfLastInsertedRecordOfTw);
        log.info("Records received from twitter:" + recordsFromTw.size());
        return recordsFromTw;
    }

    private Record getTweetData(Tweet tw) {

        String userName = "";
        String userProfileUrl = "";
        String sourceUrl = "";
        String userPhotoUrl = "";
        String recordPhotoUrl = "";
        for (MediaEntity ent : tw.getEntities().getMedia()) {
            recordPhotoUrl = ent.getMediaUrl();
            break;
        }
        userName = "@" + tw.getFromUser();
        userProfileUrl = "https://twitter.com/" + tw.getFromUser();
        sourceUrl = "https://twitter.com/" + tw.getFromUser() + "/status/" + tw.getId();
        Long dateOfCreate = Long.valueOf(tw.getCreatedAt().getTime()) / 1000L;
        userPhotoUrl = tw.getProfileImageUrl();

        return new Record(1, userName, sourceUrl, "twitter", userProfileUrl, userPhotoUrl, tw.getText(), recordPhotoUrl, dateOfCreate);

    }

}
