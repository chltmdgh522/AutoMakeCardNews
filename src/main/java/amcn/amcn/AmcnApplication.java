package amcn.amcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AmcnApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmcnApplication.class, args);
	}

}
