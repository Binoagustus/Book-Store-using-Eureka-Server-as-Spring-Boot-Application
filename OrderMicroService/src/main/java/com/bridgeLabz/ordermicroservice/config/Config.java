package com.bridgeLabz.ordermicroservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
