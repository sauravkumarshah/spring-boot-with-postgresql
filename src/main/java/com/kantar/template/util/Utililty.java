package com.kantar.template.util;

import java.util.NoSuchElementException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kantar.template.entity.Employee;
import com.kantar.template.response.EmployeeDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utililty {
	private Utililty() {
	}

	public static NoSuchElementException notFound(Integer empId) {
		log.error("Employee with id=" + empId + " not found.");
		return new NoSuchElementException("Employee with id=" + empId + " not found.");
	}

	public static EmployeeDTO mapToEmployeeDTO(Employee emp) {
		EmployeeDTO employeeDTO = EmployeeDTO.builder().id(emp.getId()).name(emp.getName()).address(emp.getAddress())
				.build();

		log.info("Employee details : {}", employeeDTO);
		return employeeDTO;
	}

	public static UsernameNotFoundException usernameNotFoundException(String msg) {
		log.error("{}", msg);
		return new UsernameNotFoundException(msg);
	}
}