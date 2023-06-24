package com.springboot.postgres;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.postgres.entity.Employee;
import com.springboot.postgres.repository.IRepository;
import com.springboot.postgres.request.EmployeeRequest;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class SpringBootPostgresqlApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private IRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	@Container
	private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11.1")
			.withDatabaseName("integration-tests-db").withUsername("username").withPassword("password")
			.withInitScript("test-data.sql");

	static {
		postgreSQLContainer.start();
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}

	@Test
	@Order(value = 1)
	void testAddEmployees() throws Exception {
		List<EmployeeRequest> employees = getEmployees();
		for (EmployeeRequest employee : employees) {
			String emp = objectMapper.writeValueAsString(employee);
			mockMvc.perform(
					MockMvcRequestBuilders.post("/api/v1/save").contentType(MediaType.APPLICATION_JSON).content(emp))
					.andExpect(status().isCreated());
		}
		Assertions.assertEquals(5, repository.findAll().size());
	}

	@Test
	@Order(value = 2)
	void testGetAllEmployees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees")).andExpect(status().isOk());
		Assertions.assertEquals(getEmployees().get(3).getName(), repository.findById(4).get().getName());
	}

	@Test
	@Order(value = 3)
	void testGetEmployeeById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/2")).andExpect(status().isOk());
		Assertions.assertEquals(getEmployees().get(1).getName(), repository.findById(2).get().getName());
	}

	@Test
	@Order(value = 4)
	void testDeleteEmployeeById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/delete/2")).andExpect(status().isOk());
	}

	@Test
	@Order(value = 5)
	void testUpdateEmployee() throws Exception {
		Employee employee = Employee.builder().id(3).name("Saurav Kumar Shah").address("India East").build();
		String emp = objectMapper.writeValueAsString(employee);
		mockMvc.perform(
				MockMvcRequestBuilders.put("/api/v1/update").contentType(MediaType.APPLICATION_JSON).content(emp))
				.andExpect(status().isOk());
		Assertions.assertEquals(employee.getName(), repository.findById(3).get().getName());
	}

	@Test
	@Order(value = 6)
	void testDeleteAllEmployees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/deleteall")).andExpect(status().isOk());
		Assertions.assertEquals(0, repository.findAll().size());
	}

	private List<EmployeeRequest> getEmployees() {
		List<EmployeeRequest> employees = new ArrayList<>();

		EmployeeRequest emp1 = EmployeeRequest.builder().name("test emp1").address("address1").build();
		EmployeeRequest emp2 = EmployeeRequest.builder().name("test emp2").address("address2").build();
		EmployeeRequest emp3 = EmployeeRequest.builder().name("test emp3").address("address3").build();
		EmployeeRequest emp4 = EmployeeRequest.builder().name("test emp4").address("address4").build();
		EmployeeRequest emp5 = EmployeeRequest.builder().name("test emp5").address("address5").build();

		employees.add(emp1);
		employees.add(emp2);
		employees.add(emp3);
		employees.add(emp4);
		employees.add(emp5);

		return employees;
	}
}
