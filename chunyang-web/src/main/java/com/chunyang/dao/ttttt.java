package com.chunyang.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ttttt {

	@Bean(name="user")
	public UserDao user(){
		return new UserDao();
	}
	
}
