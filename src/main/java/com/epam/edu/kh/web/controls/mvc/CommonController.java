package com.epam.edu.kh.web.controls.mvc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController {

    @RequestMapping(value = { "/", "/index" })
    public final ModelAndView viewIndex(final ModelAndView model) {
        model.setViewName("/index");
        return model;
    }

}
