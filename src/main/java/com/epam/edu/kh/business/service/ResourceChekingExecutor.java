package com.epam.edu.kh.business.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import com.epam.edu.kh.business.dao.RecordDao;

public class ResourceChekingExecutor implements Runnable{

	@Autowired 
	private RecordDao recordDao;
	
	@Autowired 
	private RecordService recordService;
 
	public void run() {

		try {
			recordService.recordsController();
		} catch (IOException e) { 
			e.printStackTrace();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
	}

}
