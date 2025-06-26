package unitTest;

import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserUnitTest {

    @Test
    void userConsructorTest(){
        User user = new User("Carla", "123", Role.Admin.name());
        assertEquals("Carla", user.getName());
        assertEquals("123", user.getPassword());
        assertEquals(Role.Admin, user.getRole());
    }
}
