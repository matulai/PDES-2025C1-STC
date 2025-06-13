package SeguiTusCompras.persistence;

import SeguiTusCompras.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IProductDao extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name = ?1")
    Product getByName(String productName);

    @Query("SELECT p FROM Product p WHERE p.mlaId = ?1")
    Product getByMlaId(String id);
}
