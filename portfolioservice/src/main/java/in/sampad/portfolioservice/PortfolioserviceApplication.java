package in.sampad.portfolioservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PortfolioserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioserviceApplication.class, args);
	}

}
