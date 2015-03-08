package com.epam.edu.kh.web.controls;
 
import java.util.Collections;
import java.util.List;
import java.util.Set;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody; 

import com.epam.edu.kh.business.dao.record.RecordDao;
import com.epam.edu.kh.business.dao.tag.TagDao;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;
import com.epam.edu.kh.business.scanner.JsonScannerOfResponseVK;
import com.epam.edu.kh.business.service.record.Names; 
import com.epam.edu.kh.business.service.tag.TagService;

@Controller
public class RestController {

    @Autowired
    private RecordDao recordDao;
 
    
    @Autowired
    private TagDao tagDao;
    
    @Autowired
    private TagService tagService;

    @Autowired
    private JsonScannerOfResponseVK jsonScannerOfResponseVK;

    @RequestMapping(value = "/records/top", method = RequestMethod.GET)
    @ResponseBody
    public final List<Record> getRecordsTop() {
        List<Record> response = recordDao.getListRecords();
        Collections.reverse(response);
         return response;
    }

    @RequestMapping(value = "/records/tags", method = RequestMethod.POST)
    @ResponseBody
    public final Set<Record> getRecordsTags(@RequestBody Names names) {
         
        return tagService.getRecordsByTagName(names.getNames());
    }

    @RequestMapping(value = "/records/toptags", method = RequestMethod.GET)
    @ResponseBody
    public final List<Tag> getTagsTop() {
        
        List<Tag> toptags = tagService.getTopTags();
        Collections.reverse(toptags);
        return toptags;
        
    }

}