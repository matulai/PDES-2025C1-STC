package unitTest;

import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.report.UserReport;
import SeguiTusCompras.model.user.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    private User user;

    @Mock
    private Product mockProduct;

    @Mock
    private UserReport mockUserReport;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "password123", "Client");
        user.setReport(mockUserReport);
    }

    @Test
    void testUserConstructor_InvalidRole_ThrowsIllegalArgumentException() {
        String name = "newUser";
        String password = "newPassword";
        String invalidRoleStr = "NonExistentRole";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new User(name, password, invalidRoleStr);
        });
        
        assertTrue(exception.getMessage().contains("No enum constant SeguiTusCompras.model.user.Role." + invalidRoleStr));
    }
    
    @Test
    void testUserConstructor_NullRole_ThrowsNullPointerException() {
        String name = "newUser";
        String password = "newPassword";

        assertThrows(NullPointerException.class, () -> {
            new User(name, password, null);
        }, "Role.valueOf(null) should throw NullPointerException");
    }

    @Test
    void addToPurchases_AddsProductAndUpdatesReports() {

        user.addToPurchases(mockProduct);


        assertTrue(user.getPurchases().contains(mockProduct));
        assertEquals(1, user.getPurchases().size());


        verify(mockProduct, times(1)).increasePurchasesCounter();
        verify(mockUserReport, times(1)).addPurchase();
    }
    
    @Test
    void addToPurchases_NullUserReport_ThrowsNullPointerException() {
        user.setReport(null);
        
        assertThrows(NullPointerException.class, () -> {
            user.addToPurchases(mockProduct);
        });

    }


    @Test
    void addToFavorites_AddsProductAndUpdatesReport() {

        user.addToFavorites(mockProduct);

        assertTrue(user.getFavorites().contains(mockProduct));
        assertEquals(1, user.getFavorites().size());
        verify(mockProduct, times(1)).increaseFavoritesCounter();
    }


    @Test
    void addToQualified_WhenUserOwnsProduct_AddsQualification() {
        user.getPurchases().add(mockProduct);
        Integer score = 5;
        user.qualifyProduct(mockProduct, score);

        assertEquals(1, user.getQualifications().size());
    }


    @Test
    void addToQualified_WhenUserDoesNotOwnProduct_DoesNotAddQualification() {
        Integer score = 5;
        user.qualifyProduct(mockProduct, score);

        assertTrue(user.getQualifications().isEmpty());
    }

}
