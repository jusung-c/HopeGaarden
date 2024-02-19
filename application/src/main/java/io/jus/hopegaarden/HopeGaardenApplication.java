package io.jus.hopegaarden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "io.jus.hopegaarden")
@EnableJpaAuditing // JPA Auditing 기능 활성화 - BaseEntity
public class HopeGaardenApplication {

    public static void main(String[] args) {
        SpringApplication.run(HopeGaardenApplication.class, args);
    }

}
