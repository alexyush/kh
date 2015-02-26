package com.controls; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.dao.KittyDao;
import com.entity.HomelessKitty;
 
@Controller 
public class IndexController{ 

	@Autowired
	private KittyDao kitty;
	
	@RequestMapping(value={"/","/index"})
	   public ModelAndView viewIndex(ModelAndView model){   
		model.setViewName("/index");
	    return model;
	   }
	
	
	/*@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putSpittle(@PathVariable("id") long id) {
	
	}*/
	@RequestMapping(value="/add")
	   public ModelAndView addKitties(ModelAndView model){   
		model.setViewName("/index");
		
		
		kitty.save(new HomelessKitty(1,"kitty1","need help kitty1"));
		kitty.save(new HomelessKitty(2,"kitty2","need help kitty2"));
		kitty.save(new HomelessKitty(3,"kitty3","need help kitty3"));
		kitty.save(new HomelessKitty(4,"kitty4","need help kitty4"));
		
		model.addObject("kitties", kitty.getListKitty());
		
	    return model;
	   }
	
	/*@RequestMapping(value="/",method = RequestMethod.GET)
	 // Return a list of computer to the http client
	 public @ResponseBody List<HomelessKitty> getKitties() {
	  return kitty.getListKitty();
	 
	}*/
	
	/*
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSpittle(@PathVariable long id) {
		kitty.delete(id);
	}*/
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET/*,headers = {"Accept=text/xml, application/json"}*/)
	 
	public @ResponseBody HomelessKitty getKitty(@PathVariable long id) {
			return kitty.getKitty(id);
	}
}
		 
 


 
 
 