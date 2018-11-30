package com.shinohara.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shinohara.web.models.Employee;
import com.shinohara.web.services.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService service;

	@RequestMapping(value="/employees/index", method = RequestMethod.GET)
	public String index(Model model, Pageable pageable) {
		Page<Employee> emplist = service.getAllEmployees(pageable);
        model.addAttribute("page", emplist);
        model.addAttribute("emplist", emplist.getContent());
		return "index";

	}

	@RequestMapping(value="/employees/new", method = RequestMethod.GET)
	public String newPage(Model model) {
		model.addAttribute("employee", new Employee());
		return "new";

	}

	@RequestMapping(value="/employees/show", method = RequestMethod.GET)
	public String show(@RequestParam("id")Integer id, Model model) {
		Employee employee = service.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "show";

	}

	@RequestMapping(value="/employees/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("id")Integer id, Model model) {
		Employee employee = service.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "edit";

	}

	@RequestMapping(value="/employees/update", method = RequestMethod.POST)
	public String update(@Validated @ModelAttribute Employee employeeForm, BindingResult result, Model model, Pageable pageable) {

		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			return "edit";
		}

		// データ更新
		service.update(employeeForm);

		model.addAttribute("flush", "更新が完了しました。");
		Page<Employee> emplist = service.getAllEmployees(pageable);
        model.addAttribute("page", emplist);
        model.addAttribute("emplist", emplist.getContent());
		return "index";


	}

	@RequestMapping(value="/employees/destroy", method = RequestMethod.POST)
	public String destroy(@RequestParam("id")Integer id, Model model, Pageable pageable) {

		// データ削除
		service.delete(id);

		model.addAttribute("flush", "削除が完了しました。");
		Page<Employee> emplist = service.getAllEmployees(pageable);
        model.addAttribute("page", emplist);
        model.addAttribute("emplist", emplist.getContent());
		return "index";

	}

	@RequestMapping(value="/employees/create", method = RequestMethod.POST)
	public String create(@Validated @ModelAttribute Employee employeeForm, BindingResult result, Model model, Pageable pageable) {

		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			return "new";
		}

		// データ追加
		Employee e = service.insert(employeeForm, result);
		if (e == null) {
			model.addAttribute("errors", result.getAllErrors());
			return "new";
		}

		model.addAttribute("flush", "登録が完了しました。");
		Page<Employee> emplist = service.getAllEmployees(pageable);
        model.addAttribute("page", emplist);
        model.addAttribute("emplist", emplist.getContent());
		return "index";


	}
}
