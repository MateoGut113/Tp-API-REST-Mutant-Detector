package com.example.Mutantes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableCaching
@EnableAsync
@SpringBootApplication
public class MutantDetectorApplication {

	public static void main(String[] args) {
        SpringApplication.run(MutantDetectorApplication.class, args);

        System.out.println("\n===Aplicacion en curso===");
	}

}
