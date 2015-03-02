package com.epam.edu.kh.web.controls; 
import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody; 
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.epam.edu.kh.business.dao.RecordDao;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.service.JsonScanner;
import com.epam.edu.kh.business.service.RecordService;
import com.fasterxml.jackson.core.JsonProcessingException;


@Controller 
public class IndexController{ 

	@Autowired
	private RecordDao recordDao;
	
	@Autowired
	private RecordService recordService;
	

	@Autowired
	private JsonScanner jsonScanner;
	
	
	@RequestMapping(value={"/","/index"})
	   public ModelAndView viewIndex(ModelAndView model){   
		model.setViewName("/index");
	    return model;
	   }
	
	@RequestMapping(value="/add")
	   public ModelAndView addKitties(ModelAndView model) throws JsonProcessingException, IOException{   
		model.setViewName("/index");
		
		//recordService.insertRecord("http://vk.com/id28877180?w=wall28877180_3148"); 
		//recordService.insertRecord("http://vk.com/wall-37578612_9304");
		//recordService.insertRecord("http://vk.com/leprum?w=wall-30022666_127503");
		//recordService.insertRecord("http://vk.com/arsenal_officialnews?w=wall-23473641_4290266");
		//recordService.insertRecord("http://vk.com/by_duran?w=wall-25336774_6838");
		jsonScanner.parseJsonForTesting("http://vk.com/wall-37578612_9304");
		jsonScanner.parseJsonForTesting("http://vk.com/arsenal_officialnews?w=wall-23473641_4290266");
		return model;
	
	}
			
	@RequestMapping(value="/records/top",method = RequestMethod.GET)
	@ResponseBody   
	public List<Record> getRecordsTop(){   
		return recordDao.getListPets();
	}
	@RequestMapping(value="/records/tag",method = RequestMethod.GET)
	@ResponseBody   
	public List<Record> getRecordsTags(){   
	  
		return null; 
		
	}
	@RequestMapping(value="/records/toptags",method = RequestMethod.GET)
	@ResponseBody   
	public List<Record> getTagsTop(){    
		return null; 
	} 
	
	@RequestMapping(value="/records/vk",method = RequestMethod.GET)
	@ResponseBody   
	public List<Record> putRecordsIn(){   

		return null;
		
		
	}
	
	
	
	
	
} 