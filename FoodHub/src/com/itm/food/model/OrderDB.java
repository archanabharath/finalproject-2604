package com.itm.food.model;

import com.itm.food.dao.AbstractDomain;

public class OrderDB extends AbstractDB implements IDBAccess{

	@Override
	public <T extends AbstractDomain> String add(T object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends AbstractDomain> void update(T object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends AbstractDomain> T find(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends AbstractDomain> void delete(T object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
