package com.javatechie.spring.mockito.api.dao;


import com.javatechie.spring.mockito.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
