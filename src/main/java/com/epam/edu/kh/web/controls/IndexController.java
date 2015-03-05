package com.epam.edu.kh.web.controls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.epam.edu.kh.business.dao.RecordDao;
import com.epam.edu.kh.business.scanner.JsonScannerOfResponseVK;
import com.epam.edu.kh.business.service.RecordService;

@Controller
public class IndexController {

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private RecordService recordService;

    @Autowired
    private JsonScannerOfResponseVK jsonScannerOfResponseVK;

    @ExceptionHandler(Exception.class)
    public final ModelAndView handleError(Exception ex) {

        ModelAndView mod = new ModelAndView();
        mod.addObject("message", ex.getMessage());
        mod.setViewName("/error");
        return mod;
    }

    @RequestMapping(value = { "/", "/index" })
    public final ModelAndView viewIndex(final ModelAndView model) {
        model.setViewName("/index");
        return model;
    }

    @RequestMapping(value = "/add")
    public final ModelAndView addKitties(final ModelAndView model)
            throws Exception {

        model.setViewName("/index");
        recordService.insertRecord("http://vk.com/wall-24502885_168300");
        recordService.insertRecord("http://vk.com/wall-24502885_168299");
        recordService.insertRecord("http://vk.com/wall-24502885_168297");
        recordService.insertRecord("http://vk.com/wall-24502885_168295");
        return model;

    }
}