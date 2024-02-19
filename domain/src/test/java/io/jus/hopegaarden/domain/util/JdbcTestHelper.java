package io.jus.hopegaarden.domain.util;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class JdbcTestHelper {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void initDatabase() {
        validateH2Database();
        List<String> truncateQueries = getTruncateQueries();
        truncateAllTables(truncateQueries);
    }

    private void validateH2Database() {
        jdbcTemplate.queryForObject("SELECT H2VERSION() FROM DUAL", String.class);
    }

    private List<String> getTruncateQueries() {
        return jdbcTemplate.queryForList("SELECT CONCAT('TRUNCATE TABLE ', TABLE_NAME, ' RESTART IDENTITY', ';') AS q FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'", String.class);
    }

    private void truncateAllTables(List<String> truncateQueries) {
        for (String truncateQuery : truncateQueries) {
            jdbcTemplate.execute(truncateQuery);
        }
    }
}
