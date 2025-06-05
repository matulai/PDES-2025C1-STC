package integrationTest.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import SeguiTusCompras.Error.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import SeguiTusCompras.Service.AuthService;
import SeguiTusCompras.model.user.User;
import jakarta.transaction.Transactional;
import SeguiTusCompras.SeguiTusComprasApplication;

@SpringBootTest(classes = SeguiTusComprasApplication.class)
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Test
    @Transactional
    void testCreateUser() { 
        String name = "Raul";
        String password = "123";
        String role = "Client";

        User persistedValidUser = authService.createUser(name, password, role);
        assertEquals("Client", persistedValidUser.getRole().toString());
        assertEquals("Raul", persistedValidUser.getName());      
    }

    @Test
    @Transactional
    void testCreateUserShouldThrowIllegalArgumentExceptionWhenUserHasAnInvalidRole() {
        String name = "Raul";
        String password = "123";
        String invalidRole = "Master";
        String expectedErrorMessage = ErrorMessages.INVALID_ROLE.getMessage();
        
        IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> {
            authService.createUser(name, password, invalidRole);
        });

        assertEquals(expectedErrorMessage, thrownException.getMessage());
    }

    @Test
    @Transactional
    void testCreateUserShouldThrowRunTimeExceptionWhenUserAlreadyExists() {
        String name = "Raul";
        String password = "123";
        String role = "Client";
        authService.createUser(name, password, role);
        String expectedErrorMessage = ErrorMessages.ALREADY_REGISTERED.getMessage();

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            authService.createUser(name, password, role);
        });

        assertEquals(expectedErrorMessage, thrownException.getMessage());
    }

    @Test 
    @Transactional
    void testGetUser(){
        String name = "Raul";
        String password = "123";
        String role = "Client";
        authService.createUser(name, password, role);

        User persistedUser = authService.getUser(name, password);
        assertEquals(name, persistedUser.getName());
        assertEquals(role, persistedUser.getRole().toString());
    }

    @Test
    @Transactional
    void testGetUserShouldThrowErrorWhenUserDoesNotExists(){
        String name = "Raul";
        String password = "123";
        String expectedErrorMessage = ErrorMessages.INVALID_PASSWORD_OR_USERNAME.getMessage();

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            authService.getUser(name, password);
        });
        assertEquals(expectedErrorMessage, thrownException.getMessage());
    }

    @Test
    @Transactional
    void testGetUserShouldThrowErrorWhenPasswordIsIncorrect(){
        String name = "Raul";
        String password = "123";
        String role = "Client";
        String wrongPassword = "1234";
        String expectedErrorMessage = ErrorMessages.INVALID_PASSWORD_OR_USERNAME.getMessage();

        authService.createUser(name, password, role);
        IllegalArgumentException thrownError = assertThrows(IllegalArgumentException.class, () -> {
            authService.getUser(name, wrongPassword);
        });
        assertEquals(expectedErrorMessage, thrownError.getMessage());
    }

    @Test
    @Transactional
    void testGetUserByName(){
        String name = "Raul";
        String password = "123";
        String role = "Client"; 
        authService.createUser(name, password, role);

        User persistedUser = authService.getUserByName(name);
        assertEquals(name, persistedUser.getName());
        assertEquals(role, persistedUser.getRole().toString());
    }

    @Test
    @Transactional
    void testGetUserByNameShouldThrowErrorWhenUserDoesNotExists(){
        String name = "Raul";
        String expectedErrorMessage = ErrorMessages.USER_NOT_FOUND.getMessage();

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            authService.getUserByName(name);
        });
        assertEquals(expectedErrorMessage, thrownException.getMessage());
    }

}
