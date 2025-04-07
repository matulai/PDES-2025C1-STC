package SeguiTusCompras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeguiTusComprasApplication {

	public static void main(String[] args) {
		// Ejecutar la aplicación
		SpringApplication.run(SeguiTusComprasApplication.class, args);

		// Imprimir las variables de entorno al inicio
		System.out.println("SPRING_DATASOURCE_URL: " + System.getenv("SPRING_DATASOURCE_URL"));
		System.out.println("SPRING_DATASOURCE_USERNAME: " + System.getenv("SPRING_DATASOURCE_USERNAME"));
		System.out.println("SPRING_DATASOURCE_PASSWORD: " + System.getenv("SPRING_DATASOURCE_PASSWORD"));
	}
}

