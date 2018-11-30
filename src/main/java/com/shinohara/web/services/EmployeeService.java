package com.shinohara.web.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.shinohara.web.models.Employee;
import com.shinohara.web.models.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository repository;

	public Page<Employee> getAllEmployees(Pageable pageable){
		return repository.findAll(pageable);
	}

	public Employee getEmployeeById(Integer id){
		return repository.findById(id);
	}

	public Employee getEmployeeByCode(String code) {
		return repository.findByCode(code);
	}

	public List<Employee> get15EmployeesByOrderByIdDesc() {
		return repository.findFirst15ByOrderByIdDesc();
	}
	public Employee save (Employee e) {
		return repository.save(e);
	}

	public Employee insert(Employee form, BindingResult result) {

		// 既に登録されているかチェック
		Employee duplicate = getEmployeeByCode(form.getCode());
		if (duplicate != null) {
			result.reject("errors","入力された社員番号の情報は既に存在しています。");
			return null;
		}

		Timestamp time_at = new Timestamp(System.currentTimeMillis());
		Employee e = new Employee();
		e.setCode(form.getCode());
		e.setName(form.getName());
		e.setAdmin_flag(form.getAdmin_flag());
		e.setDelete_flag(0);
		e.setCreated_at(time_at);
		e.setUpdated_at(time_at);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		e.setPassword(encoder.encode(form.getPassword()));
		return save(e);
	}

	public void update(Employee form) {
		Employee e = getEmployeeById(form.getId());
		e.setName(form.getName());
		e.setAdmin_flag(form.getAdmin_flag());
		e.setCode(form.getCode());
		e.setUpdated_at(new Timestamp(System.currentTimeMillis()));
		e.setDelete_flag(0);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		e.setPassword(encoder.encode(form.getPassword()));
		save(e);
	}

	public void delete(Integer id) {
		Employee e = getEmployeeById(id);
		e.setUpdated_at(new Timestamp(System.currentTimeMillis()));
		e.setDelete_flag(1);
		save(e);
	}

}
