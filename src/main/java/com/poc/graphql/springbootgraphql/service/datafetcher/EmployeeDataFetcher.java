package com.poc.graphql.springbootgraphql.service.datafetcher;

import com.poc.graphql.springbootgraphql.model.Employee;
import com.poc.graphql.springbootgraphql.repository.EmployeeRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDataFetcher implements DataFetcher<Employee> {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee get(DataFetchingEnvironment dataFetchingEnvironment) {

        String isn = dataFetchingEnvironment.getArgument("employeeId");

        return employeeRepository.findById(isn).get();
    }
}
