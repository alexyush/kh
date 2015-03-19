package com.epam.edu.kh.business.social.service; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component; 
import com.epam.edu.kh.business.service.record.RecordService;
import com.epam.edu.kh.business.social.reader.SocialReader;

@Component("socialService")
public class SocialService {

    @Autowired
    @Qualifier("socialReaderVk")
    private SocialReader socialReaderVk;
    
    @Autowired
    @Qualifier("socialReaderTwitter")
    private SocialReader socialReaderTw;

    @Autowired
    @Qualifier("recordServiceImpl")
    private RecordService recordService;

    public final void searchForNewRecords() {

            recordService.saveBatch(socialReaderVk.getNewRecords());
            recordService.saveBatch(socialReaderTw.getNewRecords());
    }
}
