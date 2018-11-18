package com.shinohara.web.models;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

//	@Query(name = "getAllEmployees")
//    public List<Employee> findByNamedQuery();

	public List<Employee> findFirst15ByOrderByIdDesc();
	
	public Employee findById(Integer id);

}
