import java.io.IOException;
import java.util.List;

import org.junit.Test; 

import com.epam.edu.kh.business.entity.Record; 
import com.epam.edu.kh.business.scanner.SocialScannerVK; 

import static org.junit.Assert.*;
public class SocialScannerVkTest {

    private SocialScannerVK socialScanner = new SocialScannerVK();
    
    /*@Test
    public final void testNotEmptyList() throws IOException{
        
        assertTrue(socialScanner.parseResponse("ÄîáðàåÑýðöà", "").size()>0);
    }
    @Test
    public final void testNumberOfReturnedElements() throws IOException{
        
        assertTrue(socialScanner.parseResponse("ÄîáðàåÑýðöà", "1426156326").size()==1);
    }
    @Test
    public final void testForVerifyOriginalData() throws IOException{
        
        List<Record> list = socialScanner.parseResponse("ÄîáðàåÑýðöà", "1426156326");
        
        assertEquals(list.get(0).getId(),1);
        assertEquals(list.get(0).getMessage(),"ñïàñåíèå êîòÿò íåäîðîãî!!!!!!#ÄîáðàåÑýðöà ðóëèèò");
        assertEquals(list.get(0).getUserName(),"ggggggg"); 
        assertEquals(list.get(0).getUserPhotoUrl(),"http://cs622122.vk.me/v622122295/1f7eb/YmYBdE2suZk.jpg"); 
    }
    @Test
    public final void testNullPointer() throws IOException{
        
        System.out.println(socialScanner.parseResponse("ÄîáðàåÑýðöà", "1426156327").size());
        assertTrue(socialScanner.parseResponse("ÄîáðàåÑýðöà", "1426156327").size()==0);
    }*/
    @Test
    public final void testMyTest() throws IOException{
        
        Record record = new Record();
        record.setSourceUrl("http://vk.com/wall265302295_95");
        
        socialScanner.getNewestDataForUpdate(record);
        
    }
}