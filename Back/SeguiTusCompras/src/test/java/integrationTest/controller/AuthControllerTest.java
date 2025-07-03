package integrationTest.controller;

import SeguiTusCompras.Controller.dtos.LoginDto;
import SeguiTusCompras.Controller.dtos.RegisterDto;
import SeguiTusCompras.Controller.dtos.UserDto;
import SeguiTusCompras.SeguiTusComprasApplication;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SeguiTusComprasApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    @Transactional
    @Rollback // Asegura que el usuario creado se elimine después del test
    void register_WithValidData_ShouldReturnOkAndTokenInHeader() {
        // Arrange: Preparamos los datos para el registro
        RegisterDto registerDto = new RegisterDto();
        registerDto.setName("testUserRegister");
        registerDto.setPassword("ValidPassword123!");
        registerDto.setRole("Client");

        HttpEntity<RegisterDto> requestEntity = new HttpEntity<>(registerDto);
        String url = baseUrl + "/auth/register";

        ResponseEntity<UserDto> response = restTemplate.postForEntity(url, requestEntity, UserDto.class);
        String token = response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("testUserRegister", response.getBody().getName());
        assertNotNull(token);
        assertTrue(token.startsWith("Bearer "));
    }

    @Test
    @Transactional
    @Rollback // Asegura que el usuario creado para el test se elimine
    void login_WithExistingUser_ShouldReturnOkAndToken() {
        LoginDto loginDto = new LoginDto();
        loginDto.setName("Lucia");
        loginDto.setPassword("Password123!");

        HttpEntity<LoginDto> requestEntity = new HttpEntity<>(loginDto);
        String url = baseUrl + "/auth/login";

        ResponseEntity<UserDto> response = restTemplate.postForEntity(url, requestEntity, UserDto.class);
        String token = response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Lucia", response.getBody().getName());
        assertNotNull(token);
        assertTrue(token.startsWith("Bearer "));
    }
}
