package com.epam.edu.kh.business.dao;

import java.util.List;
 
import com.epam.edu.kh.business.entity.HomelessPet;

public interface PetDao {
	void save(HomelessPet pet); 
	HomelessPet getPet(long id);
	List<HomelessPet> getListPets();
	void delete(long id);
}
