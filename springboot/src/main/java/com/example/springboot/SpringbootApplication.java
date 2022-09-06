package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableJpaRepositories
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	@RequestMapping(value = "/")
	public String hello() {
		return "Hello World";
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
//	String idForEncode = "bcrypt";
//	Map encoders = new HashMap<>();
//	encoders.put(idForEncode, new BCryptPasswordEncoder());
//	encoders.put("noop", NoOpPasswordEncoder.getInstance());
//	encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
//	encoders.put("scrypt", new SCryptPasswordEncoder());
//	encoders.put("sha256", new StandardPasswordEncoder());
//	encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
//
//	PasswordEncoder passwordEncoder =
//			new DelegatingPasswordEncoder(idForEncode, encoders);
}
