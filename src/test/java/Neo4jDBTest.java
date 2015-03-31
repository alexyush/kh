
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.transaction.annotation.Transactional;





import com.epam.edu.kh.business.domain.Tag;
import com.epam.edu.kh.business.repository.TagRepository;

import static org.junit.Assert.*;

public class Neo4jDBTest {
 
    
    @Autowired
    private TagRepository tagRepo;
    

    @Test @Transactional
    public final void saveTagTest() {
        
        Tag tg = new Tag("tag1");
        tagRepo.save(tg);
        assertEquals("tag1",tagRepo.findOne(0L));
    }
}
