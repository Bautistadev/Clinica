package com.SpringBoot.Clinica;

import com.SpringBoot.Clinica.Entity.Enum.Role;
import com.SpringBoot.Clinica.Entity.Enum.Status;
import com.SpringBoot.Clinica.Entity.UserEntity;
import com.SpringBoot.Clinica.Repository.UserRepository;
import org.openapitools.configuration.SpringDocConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;


@SpringBootApplication
@ComponentScan
@Import({SpringDocConfiguration.class})
public class MainApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);

	public static void main(String[] args) {

		LOGGER.info("START SPRINBOOT SERVICE");
		ApplicationContext ctx = SpringApplication.run(MainApplication.class, args);
	}

	@Autowired
	private ApplicationContext context;


	@Override
	public void run(String... args) throws Exception {


	}
}
