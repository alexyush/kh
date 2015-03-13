package com.epam.edu.kh.business.scanner;

import java.io.IOException;
import java.util.List;
import org.apache.http.client.ClientProtocolException;
import com.epam.edu.kh.business.entity.Record;

public interface SocialReader {

    String getResponse(String tag, String startTime)
            throws ClientProtocolException, IOException;

    List<Record> parseResponse(String tag, String startTime)
            throws ClientProtocolException, IOException;

    void getAndSaveNewRecords(String tag, String startTime);

    Record getNewestDataForUpdate(Record record)
            throws ClientProtocolException, IOException;

}
