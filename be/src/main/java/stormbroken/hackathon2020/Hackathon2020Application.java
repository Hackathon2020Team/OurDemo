package stormbroken.hackathon2020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Hackathon2020Application {

    public static void main(String[] args) {
        SpringApplication.run(Hackathon2020Application.class, args);
    }

}
