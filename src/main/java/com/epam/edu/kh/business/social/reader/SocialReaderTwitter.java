package com.epam.edu.kh.business.social.reader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import com.epam.edu.kh.business.entity.Record;

@Component
public class SocialReaderTwitter implements SocialReader {

    @Value("${value}")
    private String tag;

    @Value("${consumerKey}")
    private String consumerKey;

    @Value("${consumerSecret}")
    private String consumerSecret;

    @Value("${accessToken}")
    private String accessToken;

    @Value("${accessTokenSecret}")
    private String accessTokenSecret;

    public List<Record> getNewRecordsByTag() {

        Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret,
                accessToken, accessTokenSecret);

        List<Record> recordsFromTwitter = new ArrayList<Record>();

        SearchResults results = twitter.searchOperations().search(tag); 
        System.out.println("Count of tweets:" + results.getTweets().size());
        for (Tweet tw : results.getTweets()) {

            String userName = "";
            String userProfileUrl = "";
            String sourceUrl = "";
            String userPhotoUrl = "";

            userName = "@" + tw.getFromUser();
            userProfileUrl = "https://twitter.com/" + tw.getFromUser();

            sourceUrl = "https://twitter.com/" + tw.getFromUser() + "/status/"
                    + tw.getId();
            Long numberOfUniqueIndex = tw.getId();
            userPhotoUrl = tw.getProfileImageUrl();

            recordsFromTwitter.add(new Record(1, userName, sourceUrl,
                    "twitter", userProfileUrl, userPhotoUrl, tw.getText(),"",
                    numberOfUniqueIndex));
        }
        return recordsFromTwitter;
    }
}
