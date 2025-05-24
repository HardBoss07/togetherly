package ch.bosshard.matteo.togetherly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ch.bosshard.matteo.togetherly")
public class TogetherlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TogetherlyApplication.class, args);
	}

}
