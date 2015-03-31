package com.epam.edu.kh.business.social.reader;

import java.util.List; 

import com.epam.edu.kh.business.domain.Record;

public interface SocialReader {

    List<Record> getNewRecords(); 

}
