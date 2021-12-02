package com.javatechie.spring.mockito.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.javatechie.spring.mockito.api.dao.EmployeeRepository;
import com.javatechie.spring.mockito.api.model.Employee;
import com.javatechie.spring.mockito.api.model.Response;

@RestController
@RequestMapping("/EmployeeService")
public class EmployeeController {
	@Autowired
	private EmployeeRepository repository;

	@PostMapping("/addEmployee")
	public Response addEmployee(@RequestBody Employee emp) {

		if(emp.getName().length()>= 2) {
			emp = (Employee) repository.save(emp);
			if (emp == null)
				return new Response("employee not inserted", Boolean.FALSE);
			else
				return new Response(emp.getId() + " inserted", Boolean.TRUE);
		}
		else {
			return new Response("employee not inserted", Boolean.FALSE);

		}

	}

	@GetMapping("/employee/{id}")
	public Employee getemployee(@PathVariable("id") int id) throws Exception{
		return repository.findById(id).orElseThrow(() -> new Exception());
	}

	@PutMapping("updateEmployee/{id}")
	public Response updateEmployee(@RequestBody Employee employee, @PathVariable int id){
		repository.save(employee);

		return new Response(employee.getId()+" updated",Boolean.TRUE);
	}

	@GetMapping("/getEmployees")
	public Response getAllEmployees() {
		List<Employee> employees = (List<Employee>) repository.findAll();
		return new Response("record counts : " + employees.size(), Boolean.TRUE);
	}

	@DeleteMapping("/deleteEmployee/{id}")
	public Response delete_employee(@PathVariable("id") int id){

		if(repository.existsById(id)) {
			repository.deleteById(id);
			return new Response("deleted ", Boolean.TRUE);
		}
		else{
			return new Response("not deleted : object not found", Boolean.FALSE);

		}
	}

	// TDD (Test Driven Developement)
}
