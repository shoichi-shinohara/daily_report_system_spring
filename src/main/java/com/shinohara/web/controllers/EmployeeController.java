package com.shinohara.web.controllers;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shinohara.web.models.Employee;
import com.shinohara.web.models.EmployeeRepository;

import utils.EncryptUtil;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeRepository repository;

	@Autowired
	private Environment environment;

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
	public String update(@Validated @ModelAttribute Employee employee, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			return "edit";
		} else {
			employee.setUpdated_at(new Timestamp(System.currentTimeMillis()));
			employee.setDelete_flag(0);

			employee.setPassword(
                    EncryptUtil.getPasswordEncrypt(
                    		employee.getPassword(), environment.getProperty("salt")
                    		)
                    );
			repository.save(employee);

			model.addAttribute("flush", "更新が完了しました。");
			List<Employee> emplist = repository.findFirst15ByOrderByIdDesc();
			model.addAttribute("emplist", emplist);
			return "index";
		}

	}

	@RequestMapping(value="/employees/destroy", method = RequestMethod.POST)
	public String destroy(@RequestParam("id")Integer id, Model model) {

		Employee employee = repository.findById(id);
		model.addAttribute("employee", employee);
		employee.setUpdated_at(new Timestamp(System.currentTimeMillis()));
		employee.setDelete_flag(1);
		repository.save(employee);

		model.addAttribute("flush", "削除が完了しました。");
		List<Employee> emplist = repository.findFirst15ByOrderByIdDesc();
		model.addAttribute("emplist", emplist);
		return "index";

	}

	@RequestMapping(value="/employees/create", method = RequestMethod.POST)
	public String create(@Validated @ModelAttribute Employee employee, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			return "new";
		} else {
			Timestamp time_at = new Timestamp(System.currentTimeMillis());
			employee.setCreated_at(time_at);
			employee.setUpdated_at(time_at);
			employee.setDelete_flag(0);

			employee.setPassword(
                    EncryptUtil.getPasswordEncrypt(
                    		employee.getPassword(), environment.getProperty("salt")
                    		)
                    );

			repository.save(employee);

			model.addAttribute("flush", "登録が完了しました。");
			List<Employee> emplist = repository.findFirst15ByOrderByIdDesc();
			model.addAttribute("emplist", emplist);
			return "index";
		}

	}
}
