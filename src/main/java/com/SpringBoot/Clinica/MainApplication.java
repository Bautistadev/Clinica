package com.SpringBoot.Clinica;

import com.SpringBoot.Clinica.Entity.CredentialEntity;
import com.SpringBoot.Clinica.Entity.Enum.Role;
import com.SpringBoot.Clinica.Entity.Enum.Status;
import com.SpringBoot.Clinica.Entity.UserEntity;
import com.SpringBoot.Clinica.Repository.CredentialRepository;
import com.SpringBoot.Clinica.Repository.DoctorRepository;
import com.SpringBoot.Clinica.Repository.UserRepository;
import com.SpringBoot.Clinica.Service.UserService;
import com.SpringBoot.Clinica.model.UserRequestDTO;
import org.openapitools.configuration.SpringDocConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PreDestroy;
import java.time.LocalDate;


@SpringBootApplication
@ComponentScan(basePackages = {"com.SpringBoot.Clinica"})
@Import({SpringDocConfiguration.class})
public class MainApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);

	public static void main(String[] args) {

		LOGGER.info("START SPRINBOOT SERVICE");
		ConfigurableApplicationContext ctx = SpringApplication.run(MainApplication.class, args);

	}


	@Autowired
	private ConfigurableApplicationContext context;


	@Override
	public void run(String... args) throws Exception {


	}
	@PreDestroy
	public void cleanApp(){
		this.context.close();
	}
}
