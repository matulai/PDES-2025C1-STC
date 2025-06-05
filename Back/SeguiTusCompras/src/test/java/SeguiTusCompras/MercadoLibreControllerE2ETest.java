package SeguiTusCompras;

import SeguiTusCompras.Service.AuthService;
import SeguiTusCompras.Service.Integration.MercadoLibreService;
import SeguiTusCompras.model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MercadoLibreControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoBean
    private MercadoLibreService mercadoLibreService;

    @Test
    void getProductById() {
        String baseUrl = "http://localhost:" + port;

        String productId = "MLA37964944";

        URI uri = UriComponentsBuilder
                .newInstance()
                .uri(URI.create(baseUrl + "/products/" + productId))
                .build()
                .encode()
                .toUri();

        Product product = new Product();
        product.setMlaId("MLA123456789");
        product.setName("Product test");
        product.setDomainId("MLA-COMPUTERS");
        product.setDescription("Short description");
        product.setImageURL("https://http2.mlstatic.com/D_NQ_NP_RANDOMIMAGE_012024-F.jpg");

        when(mercadoLibreService.getProductById(productId)).thenReturn(product);

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

        Product samsungCellphone = new Product();


        Product anotherSamsungCellPhone = new Product();


        List<Product> listOfProducts = List.of(
                samsungCellphone,
                anotherSamsungCellPhone
        );

        when(mercadoLibreService.searchProducts("text", text)).thenReturn(listOfProducts);


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

        Product samsungCellphone = new Product();

        Product anotherSamsungCellPhone = new Product();

        List<Product> listOfProducts = List.of(
                samsungCellphone,
                anotherSamsungCellPhone
        );

        when(mercadoLibreService.searchProducts(text, domain_id)).thenReturn(listOfProducts);

        ResponseEntity<List<Product>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
