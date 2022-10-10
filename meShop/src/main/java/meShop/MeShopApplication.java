package meShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "meShop.model")
public class MeShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeShopApplication.class, args);
	}

}
