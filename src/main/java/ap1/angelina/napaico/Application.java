package ap1.angelina.napaico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// Forzar IPv4 para evitar problemas de DNS en redes con IPv6 restringido
		System.setProperty("java.net.preferIPv4Stack", "true");
		SpringApplication.run(Application.class, args);
	}

}
