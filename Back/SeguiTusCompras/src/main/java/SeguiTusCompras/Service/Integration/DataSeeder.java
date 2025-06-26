package SeguiTusCompras.Service.Integration;

import SeguiTusCompras.Service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final AuthService authService;

    public DataSeeder(AuthService usuarioRepository) {
        this.authService = usuarioRepository;
    }

    private String password = "Password123!";

    @Override
    public void run(String... args) throws Exception {
        authService.createUser("Yamila", password, "Admin");
        authService.createUser("Lucia", password, "Client");
        authService.createUser("Magali", password, "Client");
    }
}
