package by.bsuir.bankcreditaccounting;

import by.bsuir.bankcreditaccounting.config.RsaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaProperties.class)
@SpringBootApplication
public class BankCreditAccountingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankCreditAccountingApplication.class, args);
    }
}
