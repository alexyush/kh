package com.epam.edu.kh.business.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
 
import com.epam.edu.kh.business.entity.HomelessPet;



public class PetDaoImpl implements PetDao{

	public PetDaoImpl(){
		
	}
	@Autowired
	private SessionFactory sessionFactory;
 
	@Transactional
	public void save(HomelessPet pet) {	
		sessionFactory.getCurrentSession().save(pet);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<HomelessPet> getListPets() {
		return sessionFactory.getCurrentSession().createQuery("from HomelessPet").list();
	}  
	@Transactional
	public HomelessPet getPet(long id) {
		return (HomelessPet) sessionFactory.getCurrentSession().get(HomelessPet.class, id);
	}
	@Transactional
	public void delete(long id) {
		sessionFactory.getCurrentSession().createQuery("delete from HomelessPet u where u.id:=id").setParameter("id",id).executeUpdate();
	}
 
	
}
