package integrationTest.model;

import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.PurchaseRecipe;
import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseRecipeTest {

    @Test
    void getPurchaseRecipePriceSumsAllProductPrices(){
        Product product = new Product();
        Product secondProduct = new Product();
        product.setPrice(new BigDecimal(15));
        secondProduct.setPrice(new BigDecimal(15));
        User user = new User("Carla", "123", Role.Admin.name());
        PurchaseRecipe purchaseRecipe = new PurchaseRecipe(List.of(product, secondProduct), user);
        BigDecimal actualPrice = purchaseRecipe.getPurchasePrice();
        BigDecimal expectedPrice = new BigDecimal(30);
        assertEquals(expectedPrice, actualPrice);
    }
}
