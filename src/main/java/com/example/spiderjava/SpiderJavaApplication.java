package com.example.spiderjava;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.example.spiderjava.dao")
public class SpiderJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpiderJavaApplication.class, args);
	}
}
