package SeguiTusCompras;

import SeguiTusCompras.Service.AuthService;
import SeguiTusCompras.model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled("No andan los e2e tests")
public class MercadoLibreControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthService userService;

    @AfterEach
    void cleanUsers() {
        userService.deleteUsers();
    }

    @Test
    void getProductById() {
        String baseUrl = "http://localhost:" + port;

        URI uri = UriComponentsBuilder
                .newInstance()
                .uri(URI.create(baseUrl + "/products/MLA37964944"))
                .build()
                .encode()
                .toUri();

        ResponseEntity<Product> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getProductByKeyword() {
        String baseUrl = "http://localhost:" + port;
        String text = "samsung";

        URI uri = UriComponentsBuilder
                .newInstance()
                .uri(URI.create(baseUrl + "/products/search"))
                .queryParam("text", text)
                .build()
                .encode()
                .toUri();

        ResponseEntity<List<Product>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getProductByKeywordAndDomain() {
        String baseUrl = "http://localhost:" + port;
        String text = "samsung";
        String domain_id = "MLA-CELLPHONES";

        URI uri = UriComponentsBuilder
                .newInstance()
                .uri(URI.create(baseUrl + "/products/search"))
                .queryParam("text", text)
                .queryParam("domain_id", domain_id)
                .build()
                .encode()
                .toUri();

        ResponseEntity<List<Product>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
