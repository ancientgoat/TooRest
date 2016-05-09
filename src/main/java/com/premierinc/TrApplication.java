package com.premierinc;

import com.premierinc.trest.reset.TrResetCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication
public class TrApplication {
	public static void main(String[] args) {
		TrResetCache.readAllDefinitions();
		SpringApplication.run(TrApplication.class, args);
	}
}
