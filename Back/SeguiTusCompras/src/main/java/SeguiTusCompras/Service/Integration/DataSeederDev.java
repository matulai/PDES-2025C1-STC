package SeguiTusCompras.Service.Integration;

import SeguiTusCompras.Service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataSeederDev implements CommandLineRunner {

    private final AuthService authService;

    public DataSeederDev(AuthService usuarioRepository) {
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
