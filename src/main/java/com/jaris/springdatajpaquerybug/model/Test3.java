package com.jaris.springdatajpaquerybug.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "test_3")
public class Test3 {
    @Id
    @UuidGenerator
    @Column(nullable = false)
    private UUID id;

    @Column(name = "column_3_1", nullable = false)
    private String column31;

    @Column(name = "column_3_2", nullable = false)
    private String column32;

    @Column(name = "column_3_3", nullable = false)
    private String column33;

    @Column(name = "test_duplicate_column_name", nullable = false)
    private String testDuplicateColumnName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_2_id", nullable = false)
    private Test2 test2;
}
