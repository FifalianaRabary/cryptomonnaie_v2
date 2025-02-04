package mg.working.cryptomonnaie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CryptomonnaieApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptomonnaieApplication.class, args);
	}

}
