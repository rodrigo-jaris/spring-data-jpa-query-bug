package com.jaris.springdatajpaquerybug.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "test_1")
public class Test1 {
    @Id
    @UuidGenerator
    @Column(nullable = false)
    private UUID id;

    @Column(name = "column_1_1", nullable = false)
    private String column11;

    @Column(name = "column_1_2", nullable = false)
    private String column12;

    @Column(name = "column_1_3", nullable = false)
    private String column13;

    @Column(name = "test_duplicate_column_name", nullable = false)
    private String testDuplicateColumnName;
}
