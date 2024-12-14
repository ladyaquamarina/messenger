package messenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "messenger")
public class MessengerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessengerApplication.class, args);
    }
}
