package com.blog.spring.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class MockDaoService implements DaoService {

	private static final Logger logger = Logger.getLogger(MockDaoService.class);

	private final List<Object> objects = new ArrayList<Object>();

	public void makePersist(Object any) {
		objects.add(any);
		logger.info("Make persist " + any);
	}

	public List<Object> getAll() {
		return objects;
	}
}
