package com.flavourtown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@SpringBootApplication
public class FlavourtownApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlavourtownApplication.class, args);
	}

}
