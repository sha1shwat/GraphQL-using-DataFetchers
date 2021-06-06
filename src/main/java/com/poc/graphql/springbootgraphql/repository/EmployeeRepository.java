package com.poc.graphql.springbootgraphql.repository;

import com.poc.graphql.springbootgraphql.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
}
