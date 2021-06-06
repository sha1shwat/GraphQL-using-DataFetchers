package com.poc.graphql.springbootgraphql.service.datafetcher;


import com.poc.graphql.springbootgraphql.model.Employee;
import com.poc.graphql.springbootgraphql.repository.EmployeeRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AllEmployeesDataFetcher implements DataFetcher<List<Employee>> {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> get(DataFetchingEnvironment dataFetchingEnvironment) {
        Iterable<Employee> df = employeeRepository.findAll();
        List<Employee> result = new ArrayList<>();
        df.forEach(result::add);
        return result;
    }
}
