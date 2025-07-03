package integrationTest.controller;

import SeguiTusCompras.Controller.dtos.*;
import SeguiTusCompras.SeguiTusComprasApplication;
import SeguiTusCompras.persistence.IProductDao;
import SeguiTusCompras.persistence.IPurchaseRecipeDao;
import SeguiTusCompras.persistence.IUserDao;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SeguiTusComprasApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ClientControllerTest {

    private static String token;
    private static HttpHeaders headers;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired
    private IPurchaseRecipeDao purchaseRecipeDao;

    @Autowired
    private IProductDao productDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private EntityManager em;

    @BeforeAll
    static void setUp(@Autowired TestRestTemplate restTemplate, @LocalServerPort int port) {
        String baseUrl = "http://localhost:" + port;

        RegisterDto loginUser = new RegisterDto();
        loginUser.setName("Lucia");
        loginUser.setPassword("Password123!");

        ResponseEntity<Void> loginResponse = restTemplate.postForEntity(
                baseUrl + "/auth/login",
                loginUser,
                Void.class
        );

        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        token = loginResponse.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        headers = new HttpHeaders();
        headers.setBearerAuth(token.replace("Bearer ", ""));
    }


    @Test
    @Transactional
    @Rollback(true)
    void addProductToFavoritesReturnsFavoriteListWithAddedProduct() {
        ProductDto productDto = new ProductDto("name", "mlID", new BigDecimal("15.00"),
                "url", "domanId", "description", new ArrayList<>());
        HttpEntity<ProductDto> requestEntity = new HttpEntity<>(productDto, headers);
        String url = "http://localhost:" + port + "/client/addProductToFavorites";

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("name", response.getBody().getFirst().getName());
    }

    @Test
    @Transactional
    @Rollback(true)
    void addToCartReturnsClientCartWithNewProduct(){
        ProductDto productDto = new ProductDto("name", "mlID", new BigDecimal("15.00"),
                "url", "domanId", "description", new ArrayList<>());
        HttpEntity<ProductDto> requestEntity = new HttpEntity<>(productDto, headers);
        String url = "http://localhost:" + port + "/client/addToCart";

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("name", response.getBody().getFirst().getName());
    }

    @Test
    @Transactional
    @Rollback(true)
    void purchaseProductReturnsRecipeForPurchase(){
        ProductDto productDto = new ProductDto("producto para comprar", "mlID-compra", new BigDecimal("15.00"),
                "url", "domanId", "description", new ArrayList<>());
        HttpEntity<ProductDto> addToCartRequest = new HttpEntity<>(productDto, headers);
        restTemplate.postForEntity("http://localhost:" + port + "/client/addToCart", addToCartRequest, Void.class);

        HttpEntity<Void> purchaseRequest = new HttpEntity<>(headers);
        ResponseEntity<List<PurchaseRecipeDto>> purchaseResponse = restTemplate.exchange(
                "http://localhost:" + port + "/client/purchaseProducts",
                HttpMethod.POST, purchaseRequest, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, purchaseResponse.getStatusCode());
        assertNotNull(purchaseResponse.getBody());
        assertEquals(1, purchaseResponse.getBody().size());
    }

    @Test
    @Transactional
    @Rollback(true)
    void removeFromCartRemovesProductAndReturnsEmptyCart() {
        ProductDto productDto = new ProductDto("producto a eliminar", "mlID-eliminar", new BigDecimal("10.00"),
                "url", "domanId", "description", new ArrayList<>());
        HttpEntity<ProductDto> requestEntity = new HttpEntity<>(productDto, headers);
        restTemplate.postForEntity("http://localhost:" + port + "/client/addToCart", requestEntity, Void.class);

        ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                "http://localhost:" + port + "/client/removeFromCart",
                HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty(), "El carrito debería estar vacío.");
    }

    @Test
    @Transactional
    @Rollback(true)
    void qualifyProductReturnsOkStatus() {
        ProductDto productToQualify = new ProductDto("producto para calificar", "mlID-calificar", new BigDecimal("99.99"),
                "url", "domanId", "description", new ArrayList<>());
        HttpEntity<ProductDto> addProductRequest = new HttpEntity<>(productToQualify, headers);
        restTemplate.postForEntity("http://localhost:" + port + "/client/addProductToFavorites", addProductRequest, Void.class);

        QualificationDto qualificationDto = new QualificationDto("Lucia", "producto para calificar", 5, "¡Excelente producto!");
        HttpEntity<QualificationDto> qualificationRequest = new HttpEntity<>(qualificationDto, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/client/qualifyProduct", qualificationRequest, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Transactional
    @Disabled
    void userPurchasesReturnsPaginatedListOfPurchases() {
        ProductDto productDto = new ProductDto("producto comprado", "mlID-compra", new BigDecimal("25.50"),
                "url", "domanId", "description", new ArrayList<>());
        HttpEntity<ProductDto> addToCartRequest = new HttpEntity<>(productDto, headers);
        restTemplate.postForEntity("http://localhost:" + port + "/client/addToCart", addToCartRequest, Void.class);
        restTemplate.postForEntity("http://localhost:" + port + "/client/purchaseProducts", new HttpEntity<>(headers), Void.class);

        HttpEntity<Void> getPurchasesRequest = new HttpEntity<>(headers);
        ResponseEntity<PaginationElementDto<PurchaseRecipeDto>> response = restTemplate.exchange(
                "http://localhost:" + port + "/client/userPurchases?page=1&size=5",
                HttpMethod.GET, getPurchasesRequest, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getPagination().getTotalElements());
        assertEquals(1, response.getBody().getData().size());
    }

    @Test
    @Transactional
    @Disabled
    void userFavoritesReturnsPaginatedListOfFavorites() {
        ProductDto productDto = new ProductDto("mi favorito", "mlID-favorito", new BigDecimal("123.45"),
                "url", "domanId", "description", new ArrayList<>());
        HttpEntity<ProductDto> addFavoriteRequest = new HttpEntity<>(productDto, headers);
        restTemplate.postForEntity("http://localhost:" + port + "/client/addProductToFavorites", addFavoriteRequest, Void.class);

        HttpEntity<Void> getFavoritesRequest = new HttpEntity<>(headers);
        ResponseEntity<PaginationElementDto<ProductDto>> response = restTemplate.exchange(
                "http://localhost:" + port + "/client/userFavorites?page=1&size=5",
                HttpMethod.GET, getFavoritesRequest, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getPagination().getTotalElements());
        assertEquals("mi favorito", response.getBody().getData().getFirst().getName());
    }

    @AfterEach
    void cleanUp() {
        userDao.deleteAll();
    }

}
