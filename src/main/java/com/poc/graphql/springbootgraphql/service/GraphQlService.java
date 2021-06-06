package com.poc.graphql.springbootgraphql.service;

import com.poc.graphql.springbootgraphql.model.Employee;
import com.poc.graphql.springbootgraphql.model.Manager;
import com.poc.graphql.springbootgraphql.repository.EmployeeRepository;
import com.poc.graphql.springbootgraphql.repository.ManagerRepository;
import com.poc.graphql.springbootgraphql.service.datafetcher.AllEmployeesDataFetcher;
import com.poc.graphql.springbootgraphql.service.datafetcher.EmployeeDataFetcher;
import com.poc.graphql.springbootgraphql.service.datafetcher.MangerDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class GraphQlService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Value("classpath:employees.graphql")
    Resource resource;

    private GraphQL graphQL;
    @Autowired
    private AllEmployeesDataFetcher allEmployeesDataFetcher;
    @Autowired
    private EmployeeDataFetcher employeeDataFetcher;

    @Autowired
    private MangerDataFetcher managerDataFetcher;

    @PostConstruct
    private void loadSchema() throws IOException {

        loadDataIntoDB();

        File schemaFile = resource.getFile();
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private void loadDataIntoDB() {

        Stream.of(
                new Employee("GOO7691","Goldee Narendra Udani", "Vice President"),
                new Employee("GOO8691","Shuchi Gupta","Senior Principal Software Engineer"),
                new Employee("GOO8234","Aditi Ritam", "Senior Software Engineer - II"),
                new Employee("GOO8281","Shashwat", "Software Engineer")
        ).forEach(employee -> {
            employeeRepository.save(employee);
        });

        Stream.of(
                new Manager("GOO7691","Goldee Narendra Udani",new String[]{"GOO8691"}),
                new Manager("GOO8691","Shuchi Gupta",new String[]{"GOO8234","GOO8281"})
        ).forEach(manager -> {
            managerRepository.save(manager);
        });
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allEmployees", allEmployeesDataFetcher)
                        .dataFetcher("employee", employeeDataFetcher)
                        .dataFetcher("allManagers", managerDataFetcher))
                .build();
    }


    public GraphQL getGraphQL() {
        return graphQL;
    }
}
