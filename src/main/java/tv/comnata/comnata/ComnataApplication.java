package tv.comnata.comnata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ComnataApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComnataApplication.class, args);
    }

}