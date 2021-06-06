package com.poc.graphql.springbootgraphql.repository;

import com.poc.graphql.springbootgraphql.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager,String> {
}
