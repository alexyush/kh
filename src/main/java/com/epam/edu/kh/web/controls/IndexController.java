package com.epam.edu.kh.web.controls;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.epam.edu.kh.business.dao.RecordDao;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.service.JsonScanner;
import com.epam.edu.kh.business.service.RecordService;

@Controller
public class IndexController {

	@Autowired
	private RecordDao recordDao;

	@Autowired
	private RecordService recordService;

	@Autowired
	private JsonScanner jsonScanner;

	/*@ExceptionHandler(Exception.class)
	public ModelAndView handleError(Exception ex) {
		
	   ModelAndView mod = new ModelAndView();  
	   mod.addObject("message",ex.getMessage());
	   mod.setViewName("/error");
	   return mod;
	}*/
	
	@RequestMapping(value = { "/", "/index" })
	public ModelAndView viewIndex(ModelAndView model) {
		model.setViewName("/index");
		return model;
	}

	@RequestMapping(value = "/add")
	public ModelAndView addKitties(ModelAndView model) throws Exception {
		
		model.setViewName("/index");
		//recordService.insertRecord("http://vk.com/id28877180?w=wall28877180_3148"); 
		//recordService.insertRecord("http://vk.com/arsenal_officialnews?w=wall-23473641_4290266");
		//recordService.insertRecord("http://vk.com/by_duran?w=wall-25336774_6838")  
		jsonScanner.parseJsonForTesting("http://vk.com/arsenal_officialnews?w=wall-23473641_4291773");
		jsonScanner.parseJsonForTesting("http://vk.com/wall-37578612_9304");
		return model;

	}

	@RequestMapping(value = "/records/top", method = RequestMethod.GET)
	@ResponseBody
	public List<Record> getRecordsTop() {
		return recordDao.getListRecords();
	}

	@RequestMapping(value = "/records/tag", method = RequestMethod.GET)
	@ResponseBody
	public List<Record> getRecordsTags() {

		return null;

	}

	@RequestMapping(value = "/records/toptags", method = RequestMethod.GET)
	@ResponseBody
	public List<Record> getTagsTop() {
		return null;
	}
	
	@RequestMapping(value = "/records/vk", method = RequestMethod.GET)
	@ResponseBody
	public List<Record> putRecordsIn() {
		return null;
	}

}