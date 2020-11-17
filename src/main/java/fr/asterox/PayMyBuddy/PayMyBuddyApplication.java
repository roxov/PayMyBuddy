package fr.asterox.PayMyBuddy;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PayMyBuddyApplication {
// Instance du logger avec en paramètre le nom donné à l'instance.
	private static final Logger LOGGER = LogManager.getLogger(PayMyBuddyApplication.class);

	public static void main(String[] args) throws IOException {
		LOGGER.info("Initializing PayMyBuddy");
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

	@Bean
	public HttpTraceRepository htttpTraceRepository() {
		return new InMemoryHttpTraceRepository();
	}
}
