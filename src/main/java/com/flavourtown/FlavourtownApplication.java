package com.flavourtown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@SpringBootApplication
@EnableCaching
public class FlavourtownApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlavourtownApplication.class, args);
	}
//	@PostConstruct
//	public void started(){
//		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//
//	}

}
