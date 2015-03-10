package com.epam.edu.kh.web.controls.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.epam.edu.kh.business.service.record.RecordService;

@Controller
public class CommonController {

    @Autowired
    @Qualifier("recordServiceImpl")
    private RecordService recordService;

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
        return model;

    }
}
