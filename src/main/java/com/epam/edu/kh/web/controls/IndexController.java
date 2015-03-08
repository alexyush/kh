package com.epam.edu.kh.web.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.edu.kh.business.dao.record.RecordDao;
import com.epam.edu.kh.business.dao.tag.TagDao;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.entity.Tag;
import com.epam.edu.kh.business.scanner.JsonScannerOfResponseVK;
import com.epam.edu.kh.business.service.record.RecordService;

@Controller
public class IndexController {

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private RecordService recordService;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private JsonScannerOfResponseVK jsonScannerOfResponseVK;

    /*
     * @ExceptionHandler(Exception.class) public final ModelAndView
     * handleError(Exception ex) {
     * 
     * ModelAndView mod = new ModelAndView(); mod.addObject("message",
     * ex.getMessage()); mod.setViewName("/error"); return mod; }
     */

    @RequestMapping(value = { "/", "/index" })
    public final ModelAndView viewIndex(final ModelAndView model) {
        model.setViewName("/index");
        return model;
    }

    @RequestMapping(value = "/add")
    public final ModelAndView addRecord(final ModelAndView model)
            throws Exception {

        model.setViewName("/index");
        recordService
                .insertRecord("http://vk.com/id265302295?w=wall265302295_79");
        recordService
                .insertRecord("http://vk.com/id265302295?w=wall265302295_78");

        recordService
                .insertRecord("http://vk.com/id265302295?w=wall265302295_80");

        recordService
                .insertRecord("http://vk.com/id265302295?w=wall265302295_81");
        return model;

    }

}