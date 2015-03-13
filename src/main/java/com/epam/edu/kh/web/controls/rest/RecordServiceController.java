package com.epam.edu.kh.web.controls.rest;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;
import com.epam.edu.kh.business.service.record.Names;
import com.epam.edu.kh.business.service.record.RecordService;

@Controller
public class RecordServiceController {

    @Autowired
    @Qualifier("recordServiceImpl")
    private RecordService recordService;

    @RequestMapping(value = "/records/top", method = RequestMethod.GET)
    @ResponseBody
    public final List<Record> getRecordsTop() {

        return recordService.getTopRecords();
    }

    @RequestMapping(value = "/records/tags", method = RequestMethod.POST)
    @ResponseBody
    public final Set<Record> getRecordsTags(@RequestBody Names names) {

        return recordService.getRecordsByTagsName(names);
    }

    @RequestMapping(value = "/records/toptags", method = RequestMethod.GET)
    @ResponseBody
    public final List<Tag> getTagsTop() {

        return recordService.getTopTags();

    }
}
