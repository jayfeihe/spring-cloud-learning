package com.jay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringCloudApplication
public class SleuthZipkinClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SleuthZipkinClientApplication.class, args);
	}
}
