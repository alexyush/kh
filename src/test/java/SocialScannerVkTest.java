import java.io.IOException;
import org.junit.Test; 

import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.scanner.SocialReader;
import com.epam.edu.kh.business.scanner.SocialReaderVK; 

import static org.junit.Assert.*;
public class SocialScannerVkTest {

    private SocialReader socialReader = new SocialReaderVK();
    
    /*@Test
    public final void testNotEmptyList() throws IOException{
        

        assertTrue(socialScanner.parseResponse("ДобраеСэрца", "").size()>0);
    }
    @Test
    public final void testNumberOfReturnedElements() throws IOException{
        
        assertTrue(socialScanner.parseResponse("ДобраеСэрца", "1426156326").size()==1);
    }
    @Test
    public final void testForVerifyOriginalData() throws IOException{
        
        List<Record> list = socialScanner.parseResponse("ДобраеСэрца", "1426156326");
        
        assertEquals(list.get(0).getId(),1);
        assertEquals(list.get(0).getMessage(),"спасение котят недорого!!!!!!#ДобраеСэрца рулиит");
        assertEquals(list.get(0).getUserName(),"ggggggg"); 
        assertEquals(list.get(0).getUserPhotoUrl(),"http://cs622122.vk.me/v622122295/1f7eb/YmYBdE2suZk.jpg"); 
    }
    @Test
    public final void testNullPointer() throws IOException{
        
        System.out.println(socialScanner.parseResponse("ДобраеСэрца", "1426156327").size());
        assertTrue(socialScanner.parseResponse("ДобраеСэрца", "1426156327").size()==0);
    }*/
    @Test
    public final void testMyTest() throws IOException{
        
        Record record = new Record();
        record.setSourceUrl("http://vk.com/wall-89375473_8");
        
        record = socialReader.getNewestDataForUpdate(record);
        System.out.println(record.getMessage());
        System.out.println(record.getRecordPhotoUrl());
    }
}
