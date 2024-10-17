package com.TeachingManager.TeachingManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


@SpringBootTest
@ActiveProfiles("test")
public class UserSearchTest {
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    private DataSource dataSource;

    TransactionStatus status;
    @BeforeEach
    void beforeEach() {
        System.out.println("테스트 시작전");
        // 트랜잭션 시작
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void afterEach() {
        // 트랜잭션 롤백
        transactionManager.rollback(status);
        System.out.println("테스트 종료.");
    }

    @Test
    @Sql("/sql/initial_test_schema.sql")
    void scheduleSearch() throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SHOW TABLES")) {
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        }
    }

}
