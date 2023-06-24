package com.springboot.postgres.util;

import java.util.NoSuchElementException;

import com.springboot.postgres.entity.Employee;
import com.springboot.postgres.response.EmployeeDTO;

public class EmployeeUtil {
	private EmployeeUtil() {
	}

	public static NoSuchElementException notFound(Integer empId) {
		return new NoSuchElementException("Employee with id=" + empId + " not found.");
	}

	public static EmployeeDTO mapToEmployeeDTO(Employee emp) {
		return EmployeeDTO.builder().id(emp.getId()).name(emp.getName()).address(emp.getAddress()).build();
	}
}
