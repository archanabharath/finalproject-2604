package com.itm.food.model;

import com.itm.food.dao.AbstractDomain;

public interface IDBAccess {

	<T extends AbstractDomain> String add(T object) throws Exception;

	<T extends AbstractDomain> void update(T object) throws Exception;

	<T extends AbstractDomain> T find(String id) throws Exception;

	<T extends AbstractDomain> void delete(T object) throws Exception;

	void delete(String id) throws Exception;

}
