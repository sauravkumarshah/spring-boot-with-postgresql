package com.kantar.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kantar.template.entity.Employee;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {

}
