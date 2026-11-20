package in.sampad.marketdataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MarketdataserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketdataserviceApplication.class, args);
	}

}
