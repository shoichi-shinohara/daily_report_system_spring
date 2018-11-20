package com.shinohara.web.controllers;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shinohara.web.models.Employee;
import com.shinohara.web.models.EmployeeRepository;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeRepository repository;

	@RequestMapping(value="/employees/index", method = RequestMethod.GET)
	public String index(Model model) {
		List<Employee> emplist = repository.findFirst15ByOrderByIdDesc();
		model.addAttribute("emplist", emplist);
		return "index";

	}

	@RequestMapping(value="/employees/new", method = RequestMethod.GET)
	public String newPage(Model model) {
		model.addAttribute("employee", new Employee());
		return "new";

	}

	@RequestMapping(value="/employees/show", method = RequestMethod.GET)
	public String show(@RequestParam("id")Integer id, Model model) {
		Employee employee = repository.findById(id);
		model.addAttribute("employee", employee);
		return "show";

	}
	
	@RequestMapping(value="/employees/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("id")Integer id, Model model) {
		Employee employee = repository.findById(id);
		model.addAttribute("employee", employee);
		return "edit";

	}

	@RequestMapping(value="/employees/update", method = RequestMethod.POST)
	public String update(@ModelAttribute Employee employee, Model model) {
		
		employee.setUpdated_at(new Timestamp(System.currentTimeMillis()));
		employee.setDelete_flag(0);
		repository.save(employee);
		
		List<Employee> emplist = repository.findFirst15ByOrderByIdDesc();
		model.addAttribute("emplist", emplist);
		return "index";

	}

}
