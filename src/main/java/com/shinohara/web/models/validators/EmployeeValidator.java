package com.shinohara.web.models.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.shinohara.web.models.Employee;
import com.shinohara.web.models.EmployeeRepository;

public class EmployeeValidator implements ConstraintValidator<EmployeeValid, String>{
	
	@Autowired
	EmployeeRepository repository;
	
	@Override
	public void initialize(EmployeeValid employeeValid){
	 
	}
	 
	@Override
	public boolean isValid(String in,ConstraintValidatorContext cxt){
	
		// 重複チェック
		Employee employee = repository.findByCode(in);
		if (employee != null) {
			return false;
		}
		
		return true;
	}
}
