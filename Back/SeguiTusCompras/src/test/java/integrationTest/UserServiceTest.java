package integrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import SeguiTusCompras.SeguiTusComprasApplication;
import SeguiTusCompras.Error.ErrorMessages;
import SeguiTusCompras.Service.AuthService;
import SeguiTusCompras.Service.ProductService;
import SeguiTusCompras.Service.UserService;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Qualification;
import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;
import jakarta.transaction.Transactional;
@SpringBootTest(classes = SeguiTusComprasApplication.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private ProductService productService;

    @Test
    @Transactional
    void testGetUser(){
        String name  = "Carlos";
        String password = "1234";
        String role = "Client";
        authService.createUser(name, password, role);
        User client = userService.getUser(name);
        assertEquals(name, client.getName());
        assertEquals(role, client.getRole().toString());
    }

    @Test
    @Transactional
    void testGetUserShouldThrowErrorWhenUserDoesNotExist(){
        String expectedErrorMessage = ErrorMessages.USER_NOT_FOUND.getMessage();
        String name = "Raul";

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            userService.getUser(name);
        });

        assertEquals(expectedErrorMessage, thrownException.getMessage());
    }

    @Test
    @Transactional
    void testAddToFavorites(){
        String productName = "Sopa";
        String mlaId = "1234";
        BigDecimal price = new BigDecimal("3.487");
        String imageUrl = "someUrl";
        String domainId = "someDomain";
        String description = "a";
        Product product = new Product();
        product.setName(productName);
        product.setMlaId(mlaId);
        product.setPrice(price);
        product.setImageURL(imageUrl);
        product.setDomainId(domainId);
        product.setDescription(description);
        Product persistedProduct = productService.getProduct(product);

        String name  = "Carlos";
        String password = "1234";
        String role = "Admin";
        User user = authService.createUser(name, password, role);

        userService.addProductToFavorites(user, persistedProduct);
        User userWithProduct =  userService.getUser(name);
        assertEquals(1, userWithProduct.getFavorites().size());

    }

    @Test
    @Transactional
    void testAddPurchase(){
        String productName = "Sopa";
        String mlaId = "1234";
        BigDecimal price = new BigDecimal("3.487");
        String imageUrl = "someUrl";
        String domainId = "someDomain";
        String description = "a";
        Product product = new Product();
        product.setName(productName);
        product.setMlaId(mlaId);
        product.setPrice(price);
        product.setImageURL(imageUrl);
        product.setDomainId(domainId);
        product.setDescription(description);
        Product persistedProduct = productService.getProduct(product);

        String name  = "Carlos";
        String password = "1234";
        String role = "Admin";
        User user = authService.createUser(name, password, role);

        userService.addPurchase(user, persistedProduct);
        User userWithPurchase =  userService.getUser(name);
        assertEquals(1, userWithPurchase.getPurchases().size());
    }

    @Test
    @Transactional
    void testQualifyProductWithComment(){
        String productName = "Sopa";
        String mlaId = "1234";
        BigDecimal price = new BigDecimal("3.487");
        String imageUrl = "someUrl";
        String domainId = "someDomain";
        String description = "a";
        Product product = new Product();
        product.setName(productName);
        product.setMlaId(mlaId);
        product.setPrice(price);
        product.setImageURL(imageUrl);
        product.setDomainId(domainId);
        product.setDescription(description);
        Product persistedProduct = productService.getProduct(product);

        String name  = "Carlos";
        String password = "1234";
        String role = "Admin";
        User user = authService.createUser(name, password, role);
        Integer score = 5;
        String comment = "a";
        userService.addPurchase(user, persistedProduct);

        User userWithQualification = userService.qualifyProduct(user, persistedProduct, score, comment);

        Qualification qualification = userWithQualification.getQualifications().iterator().next();
        
        assertEquals(1, userWithQualification.getQualifications().size());
        assertEquals(score, qualification.getScore());
        assertEquals(comment, qualification.getComment().getComment()); // cambiar el nombre porque es malisimo
    }

    @Test
    @Transactional
    void testQualifyProductWithoutComment(){
        String productName = "Sopa";
        String mlaId = "1234";
        BigDecimal price = new BigDecimal("3.487");
        String imageUrl = "someUrl";
        String domainId = "someDomain";
        String description = "a";
        Product product = new Product();
        product.setName(productName);
        product.setMlaId(mlaId);
        product.setPrice(price);
        product.setImageURL(imageUrl);
        product.setDomainId(domainId);
        product.setDescription(description);
        Product persistedProduct = productService.getProduct(product);
       

        String name  = "Carlos";
        String password = "1234";
        String role = "Admin";
        User user = authService.createUser(name, password, role);
        Integer score = 5;
        String comment = null;

        userService.addPurchase(user, persistedProduct);
        userService.qualifyProduct(user, persistedProduct, score, comment);
        User userWithQualification =  userService.getUser(name);
        Qualification qualification = userWithQualification.getQualifications().iterator().next();
        assertEquals(score, qualification.getScore());
        assertNull(qualification.getComment());
    }

    @Test
    @Transactional
    void testQualifyProductWithScoreUnderZeroThrowsError(){
        String productName = "Sopa";
        String mlaId = "1234";
        BigDecimal price = new BigDecimal("3.487");
        String imageUrl = "someUrl";
        String domainId = "someDomain";
        String description = "a";
        Product product = new Product();
        product.setName(productName);
        product.setMlaId(mlaId);
        product.setPrice(price);
        product.setImageURL(imageUrl);
        product.setDomainId(domainId);
        product.setDescription(description);
        Product persistedProduct = productService.getProduct(product);

        String name  = "Carlos";
        String password = "1234";
        String role = "Admin";
        User user = authService.createUser(name, password, role);
        Integer score = -33;
        String comment = "a";
        String expectedErrorMessage = ErrorMessages.INVALID_SCORE.getMessage();
        userService.addPurchase(user, persistedProduct);
        
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            userService.qualifyProduct(user, product, score, comment);
        });
        assertEquals(expectedErrorMessage, thrownException.getMessage());
    }

    @Test
    @Transactional
    void getAllUserByRole(){
        String nameAdmin  = "Carlos";
        String passwordAdmin = "1234";
        String roleAdmin = "Admin";

        String nameClient  = "Beatriz";
        String passwordClient = "1234";
        String roleClient = "Client";

        String nameClient1  = "Leandro";
        String passwordClient1 = "1234";
        String roleClient1 = "Client";

        authService.createUser(nameAdmin, passwordAdmin, roleAdmin);
        authService.createUser(nameClient, passwordClient, roleClient);
        authService.createUser(nameClient1, passwordClient1, roleClient1);
        
        Page<User> clients = userService.getAllUserByRole(Role.Client, 0);
        assertEquals(2, clients.getTotalElements());

        Page<User> admins = userService.getAllUserByRole(Role.Admin, 0);
        assertEquals(1, admins.getTotalElements());
    }

    @Test
    @Transactional
    void getPurchasesFromUser(){
        String productName = "Sopa";
        String mlaId = "1234";
        BigDecimal price = new BigDecimal("3.487");
        String imageUrl = "someUrl";
        String domainId = "someDomain";
        String description = "a";
        Product product = new Product();
        product.setName(productName);
        product.setMlaId(mlaId);
        product.setPrice(price);
        product.setImageURL(imageUrl);
        product.setDomainId(domainId);
        product.setDescription(description);
        Product persistedProduct = productService.getProduct(product);

        String name  = "Carlos";
        String password = "1234";
        String role = "Admin";
        User user = authService.createUser(name, password, role);
        userService.addPurchase(user, persistedProduct);

        List<Product> purchases = userService.getPurchasesFromUser(user.getName());

        assertEquals(1, purchases.size());
        assertEquals(productName, purchases.get(0).getName());
    }

    @Test
    @Transactional
    void getFavoritesFromUser(){
        String productName = "Sopa";
        String mlaId = "1234";
        BigDecimal price = new BigDecimal("3.487");
        String imageUrl = "someUrl";
        String domainId = "someDomain";
        String description = "a";
        Product product = new Product();
        product.setName(productName);
        product.setMlaId(mlaId);
        product.setPrice(price);
        product.setImageURL(imageUrl);
        product.setDomainId(domainId);
        product.setDescription(description);
        Product persistedProduct = productService.getProduct(product);

        String name  = "Carlos";
        String password = "1234";
        String role = "Admin";
        User user = authService.createUser(name, password, role);
        userService.addProductToFavorites(user, persistedProduct);
        List<Product> products = userService.getFavorites(name);
        assertEquals(1, products.size());
        assertEquals(productName, products.get(0).getName());
    }

    @Test
    @Transactional
    void getTopBuyers(){
        String productName = "Sopa";
        String mlaId = "1234";
        BigDecimal price = new BigDecimal("3.487");
        String imageUrl = "someUrl";
        String domainId = "someDomain";
        String description = "a";
        Product product = new Product();
        product.setName(productName);
        product.setMlaId(mlaId);
        product.setPrice(price);
        product.setImageURL(imageUrl);
        product.setDomainId(domainId);
        product.setDescription(description);
        Product persistedProduct = productService.getProduct(product);

        String nameAdmin  = "Carlos";
        String passwordAdmin = "1234";
        String roleAdmin = "Admin";

        String nameClient  = "Beatriz";
        String passwordClient = "1234";
        String roleClient = "Client";

        String nameClient1  = "Leandro";
        String passwordClient1 = "1234";
        String roleClient1 = "Client";

        User userWith3Buys = authService.createUser(nameAdmin, passwordAdmin, roleAdmin);
        User userWith2Buys = authService.createUser(nameClient, passwordClient, roleClient);
        User userWith1Buy = authService.createUser(nameClient1, passwordClient1, roleClient1);

        userService.addPurchase(userWith3Buys, persistedProduct);
        userService.addPurchase(userWith3Buys, persistedProduct);
        userService.addPurchase(userWith3Buys, persistedProduct);


        userService.addPurchase(userWith2Buys, persistedProduct);
        userService.addPurchase(userWith2Buys, persistedProduct);

        userService.addPurchase(userWith1Buy, persistedProduct);

        List <User> topBuyers = userService.getTopBuyers();
        assertEquals(3, topBuyers.size());
        assertEquals(nameAdmin, topBuyers.get(0).getName());
        assertEquals(nameClient, topBuyers.get(1).getName());
        assertEquals(nameClient1, topBuyers.get(2).getName());   
    }

}
