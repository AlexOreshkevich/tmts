package com.blog.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blog.spring.beans.MockDaoService;

@Controller
@RequestMapping("/hello")
public class MiddlewareController {

	@Autowired
	MockDaoService daoService;

	@RequestMapping(method = RequestMethod.GET)
	public String printHello(ModelMap model) throws Exception {
		model.addAttribute("message", daoService.getAll().toString());
		return "hello";
	}

	public void setDaoService(MockDaoService daoService) {
		this.daoService = daoService;
	}
}
