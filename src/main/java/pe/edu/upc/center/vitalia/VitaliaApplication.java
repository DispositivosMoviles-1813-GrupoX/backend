package pe.edu.upc.center.vitalia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VitaliaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VitaliaApplication.class, args);
	}

}
