package com.springboot.postgres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.postgres.entity.Employee;

@Repository
public interface IRepository extends JpaRepository<Employee, Integer> {

}
