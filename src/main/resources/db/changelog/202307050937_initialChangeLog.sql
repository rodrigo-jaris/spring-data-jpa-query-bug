-- liquibase formatted sql
-- changeset rodrigo-jaris:InitialSchema
CREATE TABLE test_1 (
    id UUID PRIMARY KEY,
    column_1_1 VARCHAR(255) NOT NULL,
    column_1_2 VARCHAR(255) NOT NULL,
    column_1_3 VARCHAR(255) NOT NULL,
    test_duplicate_column_name VARCHAR(255) NOT NULL
);

CREATE TABLE test_2 (
    id UUID PRIMARY KEY,
    column_2_1 VARCHAR(255) NOT NULL,
    column_2_2 VARCHAR(255) NOT NULL,
    column_2_3 VARCHAR(255) NOT NULL,
    test_duplicate_column_name VARCHAR(255) NOT NULL,
    test_1_id UUID NOT NULL,

    CONSTRAINT fk_test_1_id
        FOREIGN KEY(test_1_id) REFERENCES test_1(id)
);

CREATE TABLE test_3 (
    id UUID PRIMARY KEY,
    column_3_1 VARCHAR(255) NOT NULL,
    column_3_2 VARCHAR(255) NOT NULL,
    column_3_3 VARCHAR(255) NOT NULL,
    test_duplicate_column_name VARCHAR(255) NOT NULL,
    test_2_id UUID NOT NULL,

    CONSTRAINT fk_test_2_id
        FOREIGN KEY(test_2_id) REFERENCES test_2(id)
);