package ch.bosshard.matteo.togetherly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = "ch.bosshard.matteo.togetherly")
public class TogetherlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TogetherlyApplication.class, args);
	}

	@Bean
	CommandLineRunner printDataSourceInfo(Environment env) {
		return args -> {
			System.out.println(">>> SPRING DATASOURCE URL: " + env.getProperty("spring.datasource.url"));
			System.out.println(">>> SPRING DATASOURCE USERNAME: " + env.getProperty("spring.datasource.username"));
			System.out.println(">>> SPRING DATASOURCE PASSWORD: " + env.getProperty("spring.datasource.password"));
		};

	}
}
