package com.jaris.springdatajpaquerybug.repository;

import com.jaris.springdatajpaquerybug.model.Test1;
import com.jaris.springdatajpaquerybug.model.Test3;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface Test3Repository extends JpaRepository<Test3, UUID> {
    @Query("SELECT t3 " +
            "FROM Test3 t3 " +
            "JOIN t3.test2 t2 " +
            "JOIN t2.test1 test " +
            "WHERE test.id = :test1Id")
    List<Test3> findTest3ByTest1HqlFail(UUID test1Id, Sort sort);

    @Query("SELECT t3 " +
            "FROM Test3 t3 " +
            "JOIN t3.test2 test " +
            "WHERE test.id = :test2Id")
    List<Test3> findTest3ByTest2HqlFail(UUID test2Id, Sort sort);

    @Query("SELECT t3 " +
            "FROM Test3 t3 " +
            "JOIN t3.test2 t2 " +
            "JOIN t2.test1 x " +
            "WHERE x.id = :test1Id")
    List<Test3> findTest3ByTest1HqlSuccess(UUID test1Id, Sort sort);

    @Query("SELECT t3 " +
            "FROM Test3 t3 " +
            "JOIN t3.test2 x " +
            "WHERE x.id = :test2Id")
    List<Test3> findTest3ByTest2HqlSuccess(UUID test2Id, Sort sort);
}
