package com.epam.edu.kh.web.controls.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.epam.edu.kh.business.domain.Record;
import com.epam.edu.kh.business.domain.Tag;
import com.epam.edu.kh.business.domain.TagNames;
import com.epam.edu.kh.business.service.record.RecordService;

@Controller
public class RecordServiceController {

    @Autowired
    private RecordService recordService;

    @RequestMapping(value = "/records/top", method = RequestMethod.GET)
    @ResponseBody
    public final List<Record> getTopRecords() {
        return recordService.getTopRecords();
    }

    @RequestMapping(value = "/records/tags", method = RequestMethod.POST)
    @ResponseBody
    public final List<Record> getRecordsByTagNames(@RequestBody final TagNames tagNames) {
        return recordService.getRecordsByTagNames(tagNames);
    }
    
    @RequestMapping(value = "/records/toptags", method = RequestMethod.GET)
    @ResponseBody
    public final List<Tag> getTopTags() {
        return recordService.getTopTags();
    }
}
