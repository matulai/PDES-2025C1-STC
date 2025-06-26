package integrationTest.model;

import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.PurchaseRecipe;
import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    @Test
    void addToPurchaseAddsPurchaseRecipeToThePurchaseList(){
        Product product = new Product();
        User user = new User("Carla", "123", Role.Admin.name());
        product.setPrice(new BigDecimal(15.95));
        PurchaseRecipe purchaseRecipe = new PurchaseRecipe(List.of(product), user);
        user.addToPurchase(purchaseRecipe);
        assertEquals(user.getPurchases().size(), 1);
        assertEquals(user.getPurchases().getFirst().getUser().getName(), user.getName());
    }

    @Test
    void addToCartCanAddAProductToTheUsersCart(){
        User user = new User("Carla", "123", Role.Admin.name());
        Product product = new Product();
        product.setName("product");
        user.addToCart(product);
        assertEquals(1, user.getCart().size());
        assertEquals("product", product.getName());
    }

    @Test
    void cleanCartCleanUsersCart(){
        User user = new User("Carla", "123", Role.Admin.name());
        Product product = new Product();
        user.addToCart(product);
        user.cleanCart();
        assertEquals(0, user.getCart().size());
    }

    @Test
    void removeFromCartRemovesGivenProductFromUsersCart(){
        User user = new User("Carla", "123", Role.Admin.name());
        Product product = new Product();
        product.setMlaId("abc");
        user.addToCart(product);
        user.removeFromCart(product);
        assertEquals(0, user.getCart().size());
    }

    @Test
    void addToFavoritesAddsAProductToUsersFavorites(){
        User user = new User("Carla", "123", Role.Admin.name());
        Product product = new Product();
        user.addToFavorites(product);
        assertEquals(1, user.getFavorites().size());
    }

    @Test
    void deleteFromFavoritesDeletesProductFromUserFavorites(){
        User user = new User("Carla", "123", Role.Admin.name());
        Product product = new Product();
        product.setMlaId("1234");
        user.addToFavorites(product);
        user.deleteFromFavorites(product);
        assertEquals(0, user.getFavorites().size());
    }

    @Test
    void ownsProductIsTrueWhenTheUserAlreadyPurchasedTheProduct(){
        User user = new User("Carla", "123", Role.Admin.name());
        Product product = new Product();
        product.setPrice(new BigDecimal(15.95));
        product.setMlaId("1234");
        PurchaseRecipe purchaseRecipe = new PurchaseRecipe(List.of(product), user);
        user.addToPurchase(purchaseRecipe);
        assertTrue(user.ownsProduct(product));
    }
}
