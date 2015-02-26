package com.dao;

import java.util.List;

import com.entity.HomelessKitty;

public interface KittyDao {

	void save(HomelessKitty kitty);
	List<HomelessKitty> getListKitty();
	void delete(long id);
}
