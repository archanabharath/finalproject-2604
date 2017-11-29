package com.itm.food.model;

import com.itm.food.dao.AbstractDomain;

public interface IDBAccess {

	<T extends AbstractDomain> int add(T object) throws Exception;

	<T extends AbstractDomain> void update(T object) throws Exception;

	<T extends AbstractDomain> T find(int id) throws Exception;

	<T extends AbstractDomain> void delete(T object) throws Exception;

	void delete(String id) throws Exception;

}
