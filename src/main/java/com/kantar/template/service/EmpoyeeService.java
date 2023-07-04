package com.kantar.template.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kantar.template.entity.Employee;
import com.kantar.template.repository.IRepository;
import com.kantar.template.request.EmployeeRequest;
import com.kantar.template.response.EmployeeDTO;
import com.kantar.template.util.EmployeeUtil;

@Service
public class EmpoyeeService {

	@Autowired
	private IRepository repository;

	public List<EmployeeDTO> employees() {
		return repository.findAll().stream().map(EmployeeUtil::mapToEmployeeDTO).toList();
	}

	public EmployeeDTO save(EmployeeRequest emp) {
		Employee employee = Employee.builder().name(emp.getName()).address(emp.getAddress()).build();
		return EmployeeUtil.mapToEmployeeDTO(repository.save(employee));
	}

	public String delete(Integer empId) {
		Employee employee = repository.findById(empId).orElseThrow(() -> EmployeeUtil.notFound(empId));
		repository.delete(employee);
		return "Employee with id=" + empId + " removed";
	}

	public String deleteAll() {
		List<Employee> employees = repository.findAll();
		if (employees.isEmpty())
			return "No employees available";
		repository.deleteAll();
		return "All employees are removed.";
	}

	public EmployeeDTO employee(Integer empId) {
		Employee employee = repository.findById(empId).orElseThrow(() -> EmployeeUtil.notFound(empId));
		return EmployeeUtil.mapToEmployeeDTO(employee);
	}

	public EmployeeDTO update(EmployeeRequest emp) {
		repository.findById(emp.getId()).orElseThrow(() -> EmployeeUtil.notFound(emp.getId()));
		Employee employee = Employee.builder().id(emp.getId()).name(emp.getName()).address(emp.getAddress()).build();
		return EmployeeUtil.mapToEmployeeDTO(repository.save(employee));
	}
}
