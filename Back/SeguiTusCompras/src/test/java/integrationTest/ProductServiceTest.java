package integrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import SeguiTusCompras.SeguiTusComprasApplication;
import SeguiTusCompras.Service.AuthService;
import SeguiTusCompras.Service.ProductService;
import SeguiTusCompras.Service.UserService;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.user.User;
import jakarta.transaction.Transactional;
@SpringBootTest(classes = SeguiTusComprasApplication.class)
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Transactional
    @Test
    public void testGetProductWichIsNotInDatabase() {
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
        
        assertEquals(product, persistedProduct);
    }


    @Transactional
    @Test
    public void testGetProductWichIsInDatabase() {
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
        productService.getProduct(product);

        Product persistedProduct = productService.getProduct(product);

        assertEquals(product, persistedProduct);
    }

    @Transactional
    @Test
    public void testGetProductByName() {
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
        productService.getProduct(product);

        Product persistedProduct = productService.getProductByName(productName);

        assertEquals(product, persistedProduct);
    }

    @Transactional
    @Test
    public void testGetTopSellingProducts() {
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
        productService.getProduct(product);

        String productName1 = "Te";
        String mlaId1 = "1234";
        BigDecimal price1 = new BigDecimal("3.487");
        String imageUrl1 = "someUrl";
        String domainId1 = "someDomain";
        String description1 = "a";
        Product product1 = new Product();
        product1.setName(productName1);
        product1.setMlaId(mlaId1);
        product1.setPrice(price1);
        product1.setImageURL(imageUrl1);
        product1.setDomainId(domainId1);
        product1.setDescription(description1);
        productService.getProduct(product1);

        String productName2 = "Cafe";
        String mlaId2 = "1234";
        BigDecimal price2 = new BigDecimal("3.487");
        String imageUrl2 = "someUrl";
        String domainId2 = "someDomain";
        String description2 = "a";
        Product product2 = new Product();
        product2.setName(productName2);
        product2.setMlaId(mlaId2);
        product2.setPrice(price2);
        product2.setImageURL(imageUrl2);
        product2.setDomainId(domainId2);
        product2.setDescription(description2);
        productService.getProduct(product2);

        String nameAdmin  = "Carlos";
        String passwordAdmin = "1234";
        String roleAdmin = "Admin";
        User user = authService.createUser(nameAdmin, passwordAdmin, roleAdmin);

        userService.addPurchase(user, product);
        userService.addPurchase(user, product);
        userService.addPurchase(user, product);

        userService.addPurchase(user, product1);
        userService.addPurchase(user, product1);

        userService.addPurchase(user, product2);

        List<Product> topSellingProducts = productService.getTopSellingProducts();

        assertEquals(3, topSellingProducts.size());
        assertEquals(product, topSellingProducts.get(0));
        assertEquals(product1, topSellingProducts.get(1));
        assertEquals(product2, topSellingProducts.get(2));
    }

    @Test
    @Transactional
    void testGetTopFavoriteProducts() {
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
        productService.getProduct(product);

        String productName1 = "Te";
        String mlaId1 = "1234";
        BigDecimal price1 = new BigDecimal("3.487");
        String imageUrl1 = "someUrl";
        String domainId1 = "someDomain";
        String description1 = "a";
        Product product1 = new Product();
        product1.setName(productName1);
        product1.setMlaId(mlaId1);
        product1.setPrice(price1);
        product1.setImageURL(imageUrl1);
        product1.setDomainId(domainId1);
        product1.setDescription(description1);
        productService.getProduct(product1);

        String productName2 = "Cafe";
        String mlaId2 = "1234";
        BigDecimal price2 = new BigDecimal("3.487");
        String imageUrl2 = "someUrl";
        String domainId2 = "someDomain";
        String description2 = "a";
        Product product2 = new Product();
        product2.setName(productName2);
        product2.setMlaId(mlaId2);
        product2.setPrice(price2);
        product2.setImageURL(imageUrl2);
        product2.setDomainId(domainId2);
        product2.setDescription(description2);
        productService.getProduct(product2);

        String nameAdmin  = "Carlos";
        String passwordAdmin = "1234";
        String roleAdmin = "Admin";
        User user = authService.createUser(nameAdmin, passwordAdmin, roleAdmin);

        String nameAdmin1  = "Ernesto";
        String passwordAdmin1 = "1234";
        String roleAdmin1 = "Admin";
        User user1 = authService.createUser(nameAdmin1, passwordAdmin1, roleAdmin1);

        String nameAdmin2  = "Laura";
        String passwordAdmin2 = "1234";
        String roleAdmin2 = "Admin";
        User user2 = authService.createUser(nameAdmin2, passwordAdmin2, roleAdmin2);

        userService.addProductToFavorites(user, product);
        userService.addProductToFavorites(user1, product);
        userService.addProductToFavorites(user2, product);

        userService.addProductToFavorites(user, product1);
        userService.addProductToFavorites(user1, product1);

        userService.addProductToFavorites(user, product2);

        List<Product> topFavoriteProducts = productService.getTopFavoriteProducts();

        assertEquals(3, topFavoriteProducts.size());
        assertEquals(product, topFavoriteProducts.get(0));
        assertEquals(product1, topFavoriteProducts.get(1));
        assertEquals(product2, topFavoriteProducts.get(2));
    }
}
