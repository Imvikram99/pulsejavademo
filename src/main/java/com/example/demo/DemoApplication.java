package com.example.demo;

//import ai.apiverse.apisuite.mirror.agent.ApimonitorAutoConfiguration;
import ai.apiverse.apisuite.mirror.agent.ApimonitorAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@ImportAutoConfiguration(ApimonitorAutoConfiguration.class)

public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
