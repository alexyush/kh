package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue; 
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="HomelessKitty")
public class HomelessKitty {

	@Id @Column(name="id") 
	@GeneratedValue
	private long id;
	
	public void setId(long id)
	{
		this.id = id;
	}
	public long getId()
	{
		return this.id;
	}
	
	@Column(name="name")
	private String name;
	
	public String getName(){
		
		return this.name;
	}
	public void setName(String name){
		
		this.name = name;
	}
	@Column(name="message")
	private String message;

	public String getMessage(){
		
		return this.message;
	}
	public void setMessage(String message){
		
		this.message = message;
	}
	public HomelessKitty(){
		 
	}
	public HomelessKitty(long id,String name,String message){
		
		this.id = id;
		this.name = name;
		this.message = message;
	}
}
