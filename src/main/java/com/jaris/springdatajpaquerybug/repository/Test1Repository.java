package com.jaris.springdatajpaquerybug.repository;

import com.jaris.springdatajpaquerybug.model.Test1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Test1Repository extends JpaRepository<Test1, UUID> {
}
