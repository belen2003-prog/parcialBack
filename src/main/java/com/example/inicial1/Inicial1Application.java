package com.example.inicial1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.inicial1"})
public class Inicial1Application {

	private static final Logger logger = LoggerFactory.getLogger(Inicial1Application.class);

	/*@Autowired
	private PersonRepository personaRepository;

	 */
	public static void main(String[] args) {

		try {
			SpringApplication.run(Inicial1Application.class, args);

			System.out.println("funcionando PersonRepository");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	}

