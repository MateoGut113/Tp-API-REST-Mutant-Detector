package com.example.Mutantes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
public class MutantDetectorApplication {

	public static void main(String[] args) {
        SpringApplication.run(MutantDetectorApplication.class, args);

        System.out.println("===Aplicacion en curso===");
	}

}
