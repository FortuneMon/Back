package FortuneMonBackEnd.fortuneMon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FortuneMonApplication {

	public static void main(String[] args) {
		SpringApplication.run(FortuneMonApplication.class, args);
	}

}
