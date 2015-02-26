package com.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.entity.HomelessKitty;

public class KittyDaoImpl implements KittyDao{

	public KittyDaoImpl(){
		
	}
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void save(HomelessKitty kitty) {	
		sessionFactory.getCurrentSession().save(kitty);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<HomelessKitty> getListKitty() {
		return sessionFactory.getCurrentSession().createQuery("from HomelessKitty").list();
	}
	@Transactional
	public void delete(long id) {
		sessionFactory.getCurrentSession().createQuery("delete from HomelessKitty u where u.id=:id").setParameter("id",id).executeUpdate();
	}
	@Transactional
	public HomelessKitty getKitty(long id) {
		
		return (HomelessKitty) sessionFactory.getCurrentSession().get(HomelessKitty.class, id);
	}
	
}
