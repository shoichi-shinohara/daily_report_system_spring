package com.shinohara.web.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	public List<Employee> findFirst15ByOrderByIdDesc();

	public Employee findById(Integer id);
	public Employee findByCode(String code);

}
