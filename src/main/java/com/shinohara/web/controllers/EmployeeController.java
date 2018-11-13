package com.shinohara.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shinohara.web.models.Employee;
import com.shinohara.web.models.EmployeeRepository;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeRepository repository;
	
	@RequestMapping(value="/employees/index", method = RequestMethod.GET)
	public String index(Model model) {
		List<Employee> emplist = repository.findAll();
		model.addAttribute("emplist", emplist);
		return "index";
		
	}
	
	
	

}
