package com.epam.edu.kh.web.controls;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.edu.kh.business.dao.RecordDao;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.scanner.JsonScannerOfResponseVK;
import com.epam.edu.kh.business.service.RecordService;

@Controller
public class RestController {

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private RecordService recordService;

    @Autowired
    private JsonScannerOfResponseVK jsonScannerOfResponseVK;

    @RequestMapping(value = "/records/top", method = RequestMethod.GET)
    @ResponseBody
    public final List<Record> getRecordsTop() {
        List<Record> response = recordDao.getListRecords();
        Collections.reverse(response);
        return response;
    }

    @RequestMapping(value = "/records/tag", method = RequestMethod.GET)
    @ResponseBody
    public final List<Record> getRecordsTags() {

        return null;

    }

    @RequestMapping(value = "/records/toptags", method = RequestMethod.GET)
    @ResponseBody
    public final List<Record> getTagsTop() {
        return null;
    }

    @RequestMapping(value = "/records/vk", method = RequestMethod.GET)
    @ResponseBody
    public final List<Record> putRecordsIn() {
        return null;
    }

}