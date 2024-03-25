package com.example.hexagon.albummgt.user;

import com.example.hexagon.albummgt.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(Application::main).with(TestDemoApplication.class).run(args);
	}

}
