package unitTest;

import SeguiTusCompras.model.report.UserReport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserReportTest {
    @Test
    void addPurchaseIncreasesPurchaseCounter(){
        UserReport userReport = new UserReport();
        userReport.addPurchase();
        assertEquals(userReport.getNumberOfPurchases(), 1);
    }
}
