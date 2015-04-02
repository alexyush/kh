import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epam.edu.kh.business.domain.Record;
import com.epam.edu.kh.business.social.reader.SocialReader;
import com.epam.edu.kh.business.social.reader.SocialReaderVK;

import static org.junit.Assert.*;

public class SocialScannerVkTest {
    
    @Autowired
    @Qualifier("socialReaderVk")
    private SocialReader vk;
    List<Record> records;

    @Test
    public final void testValueDateOfCreateOfRecord() {

        Iterator<Record> recordsIt = records.iterator();
        while (recordsIt.hasNext()) {
            assertTrue(recordsIt.next().getDateOfCreate() != null);
        }

    }
    @Test
    public final void testValueSourceUrl() {

        Iterator<Record> recordsIt = records.iterator();
        while (recordsIt.hasNext()) {
            assertTrue(recordsIt.next().getSourceUrl() != null);
        }

    }
    @Test
    public final void testValueUserName() {

        Iterator<Record> recordsIt = records.iterator();
        while (recordsIt.hasNext()) {
            assertTrue(recordsIt.next().getUserName() != null);
        }

    }
    @Test
    public final void testMessageForContainsTag() throws IOException {

        Iterator<Record> recordsIt = records.iterator();
        while (recordsIt.hasNext()) {
            assertTrue(recordsIt.next().getMessage().contains("#ДобраеСэрца"));
        }
    }

    @Test
    public final void testReturnedRuesult() throws ClientProtocolException,
            IOException {
        System.out.println(records.size());
        assertTrue(records.size() > 0);
    }
}
