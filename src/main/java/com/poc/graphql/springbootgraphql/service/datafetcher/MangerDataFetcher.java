package com.poc.graphql.springbootgraphql.service.datafetcher;


import com.poc.graphql.springbootgraphql.model.Employee;
import com.poc.graphql.springbootgraphql.model.Manager;
import com.poc.graphql.springbootgraphql.repository.ManagerRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MangerDataFetcher implements DataFetcher<List<Manager>> {

    @Autowired
    ManagerRepository managerRepository;
    @Override
    public List<Manager> get(DataFetchingEnvironment dataFetchingEnvironment) {
        Iterable<Manager> df = managerRepository.findAll();
        List<Manager> result = new ArrayList<>();
        df.forEach(result::add);
        return result;
    }
}
