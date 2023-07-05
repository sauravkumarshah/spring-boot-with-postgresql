package com.kantar.template.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kantar.template.entity.Employee;
import com.kantar.template.entity.UserInfo;
import com.kantar.template.repository.IEmployeeRepository;
import com.kantar.template.repository.IUserInfoRepository;
import com.kantar.template.request.EmployeeRequest;
import com.kantar.template.response.EmployeeDTO;
import com.kantar.template.util.Utililty;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

/**
 * 
 * @author Saurav Kumar
 *
 */
@Service
public class EmpoyeeService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private IEmployeeRepository repository;

	@Autowired
	private IUserInfoRepository userInfoRepository;

	@Autowired
	private ObservationRegistry registry;

	/**
	 * 
	 * @return
	 */
	public List<EmployeeDTO> employees() {
//		return repository.findAll().stream().map(EmployeeUtil::mapToEmployeeDTO).toList();
		return Observation.createNotStarted("getEmployees", registry)
				.observe(() -> repository.findAll().stream().map(Utililty::mapToEmployeeDTO).toList());
	}

	/**
	 * 
	 * @param emp
	 * @return
	 */
	public EmployeeDTO save(EmployeeRequest emp) {
		Employee employee = Employee.builder().name(emp.getName()).address(emp.getAddress()).build();
//		return EmployeeUtil.mapToEmployeeDTO(repository.save(employee));

		return Observation.createNotStarted("saveEmployee", registry)
				.observe(() -> Utililty.mapToEmployeeDTO(repository.save(employee)));
	}

	/**
	 * 
	 * @param empId
	 * @return
	 */
	public String delete(Integer empId) {
		Employee employee = repository.findById(empId).orElseThrow(() -> Utililty.notFound(empId));
		repository.delete(employee);
		return "Employee with id=" + empId + " removed";
	}

	/**
	 * 
	 * @return
	 */
	public String deleteAll() {
		List<Employee> employees = repository.findAll();
		if (employees.isEmpty())
			return "No employees available";
		repository.deleteAll();
		return "All employees are removed.";
	}

	/**
	 * 
	 * @param empId
	 * @return
	 */
	public EmployeeDTO employee(Integer empId) {
//		Employee employee = repository.findById(empId).orElseThrow(() -> EmployeeUtil.notFound(empId));
//		return EmployeeUtil.mapToEmployeeDTO(employee);
		return Observation.createNotStarted("getEmployee", registry).observe(() -> Utililty
				.mapToEmployeeDTO(repository.findById(empId).orElseThrow(() -> Utililty.notFound(empId))));
	}

	/**
	 * 
	 * @param emp
	 * @return
	 */
	public EmployeeDTO update(EmployeeRequest emp) {
		Employee employee = repository.findById(emp.getId()).orElseThrow(() -> Utililty.notFound(emp.getId()));

		employee.setId(emp.getId());
		employee.setName(emp.getName());
		employee.setAddress(emp.getAddress());

//		return EmployeeUtil.mapToEmployeeDTO(repository.save(employee));

		return Observation.createNotStarted("updateEmployee", registry)
				.observe(() -> Utililty.mapToEmployeeDTO(repository.save(employee)));
	}

	public UserInfo addUser(UserInfo userInfo) {
		try {
			userInfo.setPassword(encoder.encode(userInfo.getPassword()));
			return userInfoRepository.save(userInfo);
		} catch (Exception e) {
			throw e;
		}

	}

	public List<UserInfo> users() {
		return userInfoRepository.findAll();
	}
}
