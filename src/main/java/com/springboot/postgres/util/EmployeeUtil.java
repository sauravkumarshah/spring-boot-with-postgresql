package com.springboot.postgres.util;

import java.util.NoSuchElementException;

import com.springboot.postgres.entity.Employee;
import com.springboot.postgres.response.EmployeeDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeUtil {
	private EmployeeUtil() {
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
}
