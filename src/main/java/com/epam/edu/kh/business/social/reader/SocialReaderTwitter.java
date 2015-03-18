package com.epam.edu.kh.business.social.reader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import com.epam.edu.kh.business.entity.Record;

@Component
public class SocialReaderTwitter implements SocialReader {

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

        SearchResults results = twitter.searchOperations().search(
                "#ДобраеСэрца");
        
        

        System.out.println("Count of tweets:" + results.getTweets().size());

        for (Tweet tw : results.getTweets()) {

            System.out.println("get id:"+tw.getId());
            System.out.println("get from user:"+tw.getFromUser());
            System.out.println("get created at:"+(tw.getCreatedAt())); 
            System.out.println("has media:"+tw.hasMedia());
            System.out.println("get source:"+tw.getSource()); 
            System.out.println("get tags:"+tw.hasTags());
            System.out.println("************");

            recordsFromTwitter.add(new Record(1, tw.getFromUser(),"",
                    "twitter", "", tw.getProfileImageUrl(), tw.getText(), "",
                    new Long(1)));
        }
        return recordsFromTwitter;
    }
}
