package com.jaris.springdatajpaquerybug.repository;

import com.jaris.springdatajpaquerybug.model.Test2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Test2Repository extends JpaRepository<Test2, UUID> {
}
