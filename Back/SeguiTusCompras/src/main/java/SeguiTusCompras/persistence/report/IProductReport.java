package SeguiTusCompras.persistence.report;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.report.ProductReport;

public interface IProductReport extends JpaRepository<ProductReport, Long> {

    @Query("SELECT pr.product FROM ProductReport pr ORDER BY pr.purchaseCount DESC")
    List<Product> getTopSellingProducts(Pageable pageable);


    @Query("SELECT pr.product FROM ProductReport pr ORDER BY pr.favoritesCount DESC")
    List<Product> getTopFavoriteProducts(Pageable topFive);
    
}
