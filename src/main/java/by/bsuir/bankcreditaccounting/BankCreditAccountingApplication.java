package by.bsuir.bankcreditaccounting;

import by.bsuir.bankcreditaccounting.config.RsaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties(RsaProperties.class)
@SpringBootApplication
public class BankCreditAccountingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankCreditAccountingApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
