package io.jus.hopegaarden.domain;

import io.jus.hopegaarden.domain.util.JdbcTestHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class HopeGaardenDomainApplication extends JdbcTestHelper {
    public static void main(String[] args) {
        SpringApplication.run(HopeGaardenDomainApplication.class, args);
    }
}
