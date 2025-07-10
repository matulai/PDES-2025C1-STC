package integrationTest.controller;

import SeguiTusCompras.Controller.dtos.*;
import SeguiTusCompras.SeguiTusComprasApplication;
import SeguiTusCompras.persistence.IProductDao;
import SeguiTusCompras.persistence.IPurchaseRecipeDao;
import SeguiTusCompras.persistence.IQualificationDAO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SeguiTusComprasApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IPurchaseRecipeDao purchaseRecipeDao;

    @Autowired
    private IProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IQualificationDAO qualificationDao;

    private String adminToken;
    private HttpHeaders adminHeaders;
    private String baseUrl;

    @BeforeEach
    void setup() {
        baseUrl = "http://localhost:" + port;

        RegisterDto adminLogin = new RegisterDto();
        adminLogin.setName("Yamila");
        adminLogin.setPassword("Password123!"); // Contraseña de admin asumida

        restTemplate.postForEntity(baseUrl + "/auth/register", adminLogin, Void.class);

        ResponseEntity<Void> loginResponse = restTemplate.postForEntity(
                baseUrl + "/auth/login", adminLogin, Void.class);

        assertEquals(HttpStatus.OK, loginResponse.getStatusCode(), "El login de admin debe funcionar para correr los tests");
        adminToken = loginResponse.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        assertNotNull(adminToken, "El token de admin no puede ser nulo");

        adminHeaders = new HttpHeaders();
        adminHeaders.setBearerAuth(adminToken.replace("Bearer ", ""));
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DELETE FROM favorite");
        jdbcTemplate.execute("DELETE FROM cart");

        purchaseRecipeDao.deleteAll();
        qualificationDao.deleteAll();

        productDao.deleteAll();
    }

    private HttpHeaders loginClient(String username, String password) {
        LoginDto clientDto = new LoginDto();
        clientDto.setName(username);
        clientDto.setPassword(password);

        // Login
        ResponseEntity<Void> loginResponse = restTemplate.postForEntity(baseUrl + "/auth/login", clientDto, Void.class);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        String clientToken = loginResponse.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        HttpHeaders clientHeaders = new HttpHeaders();
        clientHeaders.setBearerAuth(clientToken.replace("Bearer ", ""));
        return clientHeaders;
    }

    @Test
    void allRegisteredUsers_ShouldReturnPaginatedClients() {
        HttpEntity<Void> requestEntity = new HttpEntity<>(adminHeaders);
        String url = baseUrl + "/admin/allRegisteredUsers?page=1&size=5";
        ResponseEntity<PaginationElementDto<SimpleUserDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getData().isEmpty());
        assertEquals("Lucia", response.getBody().getData().getFirst().getName());
    }

    @Test
    void allUsersQualifications_ShouldReturnAllQualifications() {
        HttpHeaders clientHeaders = loginClient("Lucia", "Password123!");
        ProductDto product = new ProductDto("ProductoCalificado", "ml-calif", new BigDecimal("100"), "url", "domain", "desc", new ArrayList<>());

        restTemplate.exchange(baseUrl + "/client/addToCart", HttpMethod.POST, new HttpEntity<>(product, clientHeaders), new ParameterizedTypeReference<>() {});
        restTemplate.exchange(baseUrl + "/client/purchaseProducts", HttpMethod.POST, new HttpEntity<>(clientHeaders), new ParameterizedTypeReference<>() {});

        QualificationDto qualificationDto = new QualificationDto("Lucia", "ProductoCalificado", 5, "Excelente!");
        HttpEntity<QualificationDto> qualificationRequest = new HttpEntity<>(qualificationDto, clientHeaders);
        restTemplate.postForEntity(baseUrl + "/client/qualifyProduct", qualificationRequest, Void.class);

        HttpEntity<Void> adminRequest = new HttpEntity<>(adminHeaders);
        String url = baseUrl + "/admin/allUsersQualifications?page=1&size=5";
        ResponseEntity<PaginationElementDto<QualificationDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, adminRequest, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getData().isEmpty());
        assertEquals("Excelente!", response.getBody().getData().getFirst().getComment());
    }

    @Test
    // @Disabled
    void allFavoritesProducts_ShouldReturnAllFavorites() {
        HttpHeaders clientHeaders = loginClient("Lucia", "Password123!");
        ProductDto product = new ProductDto("ProductoFavorito", "ml-fav", new BigDecimal("99.99"), "url", "domain", "desc", new ArrayList<>());
        HttpEntity<ProductDto> addFavoriteRequest = new HttpEntity<>(product, clientHeaders);
        restTemplate.postForEntity(baseUrl + "/client/addProductToFavorites", addFavoriteRequest, Void.class);

        HttpEntity<Void> adminRequest = new HttpEntity<>(adminHeaders);
        String url = baseUrl + "/admin/allFavoritesProducts?page=1&size=5";
        ResponseEntity<PaginationElementDto<ProductDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, adminRequest, new ParameterizedTypeReference<>() {});

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getData().isEmpty());
        assertEquals("ProductoFavorito", response.getBody().getData().getFirst().getName());
    }

    @Test
    void allUsersPurchases_ShouldReturnAllPurchases() {
        HttpHeaders clientHeaders = loginClient("Lucia", "Password123!");
        ProductDto product = new ProductDto("ProductoComprado", "ml-buy", new BigDecimal("50.00"), "url", "domain", "desc", new ArrayList<>());

        restTemplate.postForEntity(baseUrl + "/client/addToCart", new HttpEntity<>(product, clientHeaders), Void.class);
        restTemplate.postForEntity(baseUrl + "/client/purchaseProducts", new HttpEntity<>(clientHeaders), Void.class);

        HttpEntity<Void> adminRequest = new HttpEntity<>(adminHeaders);
        String url = baseUrl + "/admin/allUsersPurchases?page=1&size=5";
        ResponseEntity<PaginationElementDto<PurchaseRecipeDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, adminRequest, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getData().isEmpty());
        assertEquals(1, response.getBody().getData().size());
        assertEquals("ProductoComprado", response.getBody().getData().getFirst().getPurchaseProducts().getFirst().getName());
    }

    @Test
    void topSellingProducts_ShouldReturnProductsOrderedBySales() {
        HttpHeaders client1Headers = loginClient("Lucia", "Password123!");
        ProductDto topProduct = new ProductDto("MasVendido", "ml-top", new BigDecimal("10"), "url", "domain", "desc", new ArrayList<>());
        ProductDto otherProduct = new ProductDto("MenosVendido", "ml-other", new BigDecimal("20"), "url", "domain", "desc", new ArrayList<>());

        restTemplate.postForEntity(baseUrl + "/client/addToCart", new HttpEntity<>(topProduct, client1Headers), Void.class);
        restTemplate.postForEntity(baseUrl + "/client/purchaseProducts", new HttpEntity<>(client1Headers), Void.class);
        restTemplate.postForEntity(baseUrl + "/client/addToCart", new HttpEntity<>(topProduct, client1Headers), Void.class);
        restTemplate.postForEntity(baseUrl + "/client/purchaseProducts", new HttpEntity<>(client1Headers), Void.class);

        restTemplate.postForEntity(baseUrl + "/client/addToCart", new HttpEntity<>(otherProduct, client1Headers), Void.class);
        restTemplate.postForEntity(baseUrl + "/client/purchaseProducts", new HttpEntity<>(client1Headers), Void.class);

        HttpEntity<Void> adminRequest = new HttpEntity<>(adminHeaders);
        String url = baseUrl + "/admin/topSellingProducts?page=1&size=5";
        ResponseEntity<PaginationElementDto<ProductDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, adminRequest, new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getData().size());
        assertEquals("MasVendido", response.getBody().getData().getFirst().getName());
    }
}