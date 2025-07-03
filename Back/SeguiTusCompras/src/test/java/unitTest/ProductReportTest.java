package unitTest;

import SeguiTusCompras.model.report.ProductReport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductReportTest {
    @Test
    void addPurchaseIncreasesThePurchaseCounter(){
        ProductReport productReport = new ProductReport();
        productReport.addPurchase();
        assertEquals(productReport.getPurchaseCount(), 1);
    }

    @Test
    void addFavoritesIncreasesFavoriteCounter(){
        ProductReport productReport = new ProductReport();
        productReport.addFavorite();
        assertEquals(productReport.getFavoritesCount(), 1);
    }

    @Test
    void decreaseFavoritesDeceasesFavoritesCounter(){
        ProductReport productReport = new ProductReport();
        productReport.addFavorite();
        productReport.decreaseFavorite();
        assertEquals(productReport.getFavoritesCount(), 0);
    }
}
