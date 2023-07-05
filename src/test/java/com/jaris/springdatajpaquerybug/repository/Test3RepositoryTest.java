package com.jaris.springdatajpaquerybug.repository;

import com.jaris.springdatajpaquerybug.BaseIntegrationTest;
import com.jaris.springdatajpaquerybug.model.Test1;
import com.jaris.springdatajpaquerybug.model.Test2;
import com.jaris.springdatajpaquerybug.model.Test3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class Test3RepositoryTest extends BaseIntegrationTest {
    @Autowired
    private Test1Repository test1Repository;

    @Autowired
    private Test2Repository test2Repository;

    @Autowired
    private Test3Repository test3Repository;

    private Test1 test1;
    private Test2 test2;
    private Test3 test3;

    private static final Sort SORT_BUG = Sort.by(Sort.Direction.DESC, "testDuplicateColumnName");
    private static final Sort SORT_WITH_ALIAS_BUG = Sort.by(Sort.Direction.DESC, "t3.testDuplicateColumnName");

    @BeforeEach
    void createData() {
        test1 = test1Repository.save(Test1.builder()
                .column11("column11")
                .column12("column12")
                .column13("column13")
                .testDuplicateColumnName("testDuplicateColumnName1")
                .build());
        test2 = test2Repository.save(Test2.builder()
                .column21("column21")
                .column22("column22")
                .column23("column23")
                .testDuplicateColumnName("testDuplicateColumnName2")
                .test1(test1)
                .build());
        test3 = test3Repository.save(Test3.builder()
                .column31("column31")
                .column32("column32")
                .column33("column33")
                .testDuplicateColumnName("testDuplicateColumnName3")
                .test2(test2)
                .build());
    }

    @AfterEach
    void deleteData() {
        test3Repository.delete(test3);
        test2Repository.delete(test2);
        test1Repository.delete(test1);
    }

    /**
     * Please check that the following fails. The reason it fails is the following:
     * If one is using aliases in the where clause and the property name added to the Sort/Pageable starts with
     * any of the where clause aliases, spring-data-jpa won't add the alias to the ORDER BY clause.
     * In the code below, we are sorting by testDuplicateColumnName and in the where clause we have an alias t.
     * Thus, since testDuplicateColumnName starts with t, spring-data-jpa won't add the alias "t3." To the ORDER BY clause.
     * That happens because IMO there is a bug in the following class: {@link org.springframework.data.jpa.repository.query.JpaQueryTransformerSupport}
     * The method that has the bug is the following:
     * <code>
     *  private boolean shouldPrefixWithAlias(Sort.Order order, String primaryFromAlias) {
     * 		// If there is no primary alias
     * 		if (ObjectUtils.isEmpty(primaryFromAlias)) {
     * 			return false;
     *      }
     * 		// If the Sort contains a function
     * 		if (order.getProperty().contains("(")) {
     * 			return false;
     *      }
     * 		// If the Sort references an alias directly
     * 		if (projectionAliases.contains(order.getProperty())) {
     * 			return false;
     *      }
     * 		// If the Sort property starts with an alias
     * 	    // THIS IS THE BUG. IMHO A "." MISSING AND IT SHOULD BE: alias -> order.getProperty().startsWith("%s.".formatted(alias))
     * 		if (projectionAliases.stream().anyMatch(alias -> order.getProperty().startsWith(alias))) {
     * 			return false;
     *      }
     * 		return true;*
     *  }
     * </code>
     */
    @Test
    void testFindTest3ByTest1HqlFailShouldFailWhenSortingByDuplicateColumnName() {
        var test1Id = test1.getId();
        var ex = assertThrows(
                InvalidDataAccessApiUsageException.class,
                () -> test3Repository.findTest3ByTest1HqlFail(test1Id, SORT_BUG));
        ex.printStackTrace();
    }

    // The same error happens in this test.
    @Test
    void testFindTest3ByTest2HqlFailShouldFailWhenSortingByDuplicateColumnName() {
        var test2Id = test2.getId();
        var ex = assertThrows(
                InvalidDataAccessApiUsageException.class,
                () -> test3Repository.findTest3ByTest2HqlFail(test2Id, SORT_BUG));
        ex.printStackTrace();
    }

    @Test
    void testFindTest3ByTest1HqlSuccessShouldSucceed() {
        var test1Id = test1.getId();
        assertDoesNotThrow(() -> test3Repository.findTest3ByTest1HqlSuccess(test1Id, SORT_BUG));
    }

    @Test
    void testFindTest3ByTest2HqlSuccessShouldSucceed() {
        var test2Id = test2.getId();
        assertDoesNotThrow(() -> test3Repository.findTest3ByTest2HqlSuccess(test2Id, SORT_BUG));
    }

    @Test
    void testFindTest3ByTest1HqlFailShouldFailWhenSortingByDuplicateColumnNameWithAlias() {
        var test1Id = test1.getId();
        var ex = assertThrows(
                InvalidDataAccessApiUsageException.class,
                () -> test3Repository.findTest3ByTest1HqlFail(test1Id, SORT_WITH_ALIAS_BUG));
        ex.printStackTrace();
    }

    // The same error happens in this test.
    @Test
    void testFindTest3ByTest2HqlFailShouldFailWhenSortingByDuplicateColumnNameWithAlias() {
        var test2Id = test2.getId();
        var ex = assertThrows(
                InvalidDataAccessApiUsageException.class,
                () -> test3Repository.findTest3ByTest2HqlFail(test2Id, SORT_WITH_ALIAS_BUG));
        ex.printStackTrace();
    }

    @Test
    void testFindTest3ByTest1HqlSuccessShouldFailWhenSortingByDuplicateColumnNameWithAlias() {
        var test1Id = test1.getId();
        var ex = assertThrows(
                InvalidDataAccessApiUsageException.class,
                () -> test3Repository.findTest3ByTest1HqlSuccess(test1Id, SORT_WITH_ALIAS_BUG));
        ex.printStackTrace();
    }

    @Test
    void testFindTest3ByTest2HqlSuccessShouldFailWhenSortingByDuplicateColumnNameWithAlias() {
        var test2Id = test2.getId();
        var ex = assertThrows(
                InvalidDataAccessApiUsageException.class,
                () -> test3Repository.findTest3ByTest2HqlSuccess(test2Id, SORT_WITH_ALIAS_BUG));
        ex.printStackTrace();
    }
}
