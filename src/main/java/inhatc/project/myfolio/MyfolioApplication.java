package inhatc.project.myfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyfolioApplication.class, args);
	}

}
