package com.edts.ticketapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = { "com.edts.ticketapi.controller", "com.edts.ticketapi.service"})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "com.edts.ticketapi.repository")
public class TicketApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketApiApplication.class, args);
	}

}
