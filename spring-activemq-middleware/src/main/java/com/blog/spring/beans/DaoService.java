package com.blog.spring.beans;

import java.util.List;

public interface DaoService {

	void makePersist(Object any);

	List<Object> getAll();
}
