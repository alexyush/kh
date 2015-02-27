package com.epam.edu.kh.web.controls; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody; 
import org.springframework.web.servlet.ModelAndView;

import com.epam.edu.kh.business.dao.PetDao;
import com.epam.edu.kh.business.entity.HomelessPet;


@Controller 
public class IndexController{ 

	@Autowired
	private PetDao pet;
	
	@RequestMapping(value={"/","/index"})
	   public ModelAndView viewIndex(ModelAndView model){   
		model.setViewName("/index");
	    return model;
	   }
	
	@RequestMapping(value="/obj")
	   public ModelAndView viewOb(ModelAndView model){   
		model.setViewName("/kitty");
	    return model;
	   }

	
	@RequestMapping(value="/add")
	   public ModelAndView addKitties(ModelAndView model){   
		model.setViewName("/index");
		
		
		pet.save(new HomelessPet(1,
				"Alex",
				"http://vk.com/altgraph?z=photo41632690_355234691%2Fwall-34487040_1042",
				"http://vk.com/altgraph",
				"http://cs622926.vk.me/v622926279/1910a/jK_SO7tldlo.jpg",
				"asdasdasd",
				"http://vk.com/altgraph?z=photo41632690_355234691%2Fwall-34487040_1042"
				)
		); 
		pet.save(new HomelessPet(2,
				"Alex",
				"http://vk.com/altgraph?z=photo41632690_355234691%2Fwall-34487040_1042",
				"http://vk.com/altgraph",
				"http://cs622926.vk.me/v622926279/1910a/jK_SO7tldlo.jpg",
				"asdasdasd",
				"http://vk.com/altgraph?z=photo41632690_355234691%2Fwall-34487040_1042"
				)
		);
	    return model;
	   }
			
	@RequestMapping(value="/records/top",method = RequestMethod.GET)
	@ResponseBody   
	public List<HomelessPet> getRecordsTop(){   
	  
		
		for(HomelessPet dso:pet.getListPets())
		{
			System.out.println(dso.getId());
			System.out.println(dso.getMessage());
			System.out.println(dso.getRecordPhotoUrl());
		}
		return pet.getListPets();
	}
	
	@RequestMapping(value="/records/tag",method = RequestMethod.GET)
	@ResponseBody   
	public List<HomelessPet> getRecordsTags(){   
	  
		return null; 
		
	}
	@RequestMapping(value="/records/toptags",method = RequestMethod.GET)
	@ResponseBody   
	public List<HomelessPet> getTagsTop(){    
		return null; 
	} 
} 