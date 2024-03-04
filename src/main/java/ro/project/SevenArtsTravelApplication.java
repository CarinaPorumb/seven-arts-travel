package ro.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableAutoConfiguration
@EnableConfigurationProperties
public class SevenArtsTravelApplication {
	public static void main(String[] args) {
		SpringApplication.run(SevenArtsTravelApplication.class, args);
	}

}