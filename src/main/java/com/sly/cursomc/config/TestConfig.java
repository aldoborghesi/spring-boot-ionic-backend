package com.sly.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.sly.cursomc.services.DBService;
import com.sly.cursomc.services.EmailService;
import com.sly.cursomc.services.MockMailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbservice;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbservice.instatiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService( ) {
		return new MockMailService();
	}
}
