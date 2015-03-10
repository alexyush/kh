 
import java.io.IOException;

import org.junit.Test; 
import org.springframework.security.acls.model.NotFoundException; 
 
import com.epam.edu.kh.business.entity.Record; 
import com.epam.edu.kh.business.scanner.SocialScannerVk; 

import static org.junit.Assert.*;

public class SocialScannerVkTest {
     
    SocialScannerVk json = new SocialScannerVk();
    
    @Test
    public final void testReturnedUserName() throws NullPointerException, IOException {

        Record rec1 = new Record();
        rec1.setUserName("Лепра");
        Record rec2 = json
                .parseResponse("http://vk.com/id265302295?w=wall265302295_72");
        assertEquals(rec1.getUserName(), rec2.getUserName());
    }

    @Test
    public final void testReturnedUserNameFromAnotherLinks() throws NullPointerException, IOException {

        Record rec1 = json
                .parseResponse("http://vk.com/wall-24502885_168300");
        Record rec2 = json
                .parseResponse("http://vk.com/wall-24502885_168295");
        assertEquals(rec1.getUserName(), rec2.getUserName());
    }

    @Test
    public final void testBadLink() throws NullPointerException, IOException {

        assertEquals("-24502885_168300",
                json.parseLink("http://vk.com/wall-24502885_168300"));
    }

    @Test
    public final void testReturnedPostIdFalse() throws NullPointerException, IOException {

        assertFalse("-24502885_168300".equals(json
                .parseLink("http://vk.com/wallwqerqweqwe_168300")));
    }

    @Test(expected = NotFoundException.class)
    public final void testReturnedPostIdTrue() throws NullPointerException, IOException {

        assertEquals("-24502885_168300",
                json.parseLink("http://vk.com/wa-24502885_168300"));
    }

    @Test
    public final void testReturnedMessage() throws NullPointerException, IOException {

        Record rec1 = json
                .parseResponse("http://vk.com/wall-24502885_168295");
        Record rec2 = json
                .parseResponse("http://vk.com/id265302295?w=wall265302295_72");
        assertFalse(rec1.getMessage().equals(rec2.getMessage()));
    }
}