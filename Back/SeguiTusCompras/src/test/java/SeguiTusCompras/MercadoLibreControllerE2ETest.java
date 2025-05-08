package SeguiTusCompras;

import SeguiTusCompras.Controller.dtos.RegisterDto;
import SeguiTusCompras.Service.UserService;
import SeguiTusCompras.model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.*;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MercadoLibreControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @AfterEach
    void cleanUsers() {
        userService.deleteUsers();
    }

    private String registerForToken() {
        String baseUrl = "http://localhost:" + port;

        RegisterDto newUser = new RegisterDto();
        newUser.setName("user");
        newUser.setPassword("password$1");
        newUser.setRole("Client");

        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(
                baseUrl + "/auth/register",
                newUser,
                Void.class
        );

        return registerResponse.getHeaders().getFirst("Authorization");
    }

    @Test
    void getProductById() {
        String baseUrl = "http://localhost:" + port;
        String productId = "MLA37964944";

        String token = registerForToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.replace("Bearer ", ""));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Product> response = restTemplate.exchange(
                baseUrl + "/products/" + productId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getProductByKeyword() {
        String baseUrl = "http://localhost:" + port;
        String text = "samsung";

        String token = registerForToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.replace("Bearer ", ""));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Product>> response = restTemplate.exchange(
                baseUrl + "/products/search/" + text,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
