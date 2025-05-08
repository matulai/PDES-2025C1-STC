package SeguiTusCompras.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SeguiTusCompras.model.Product;

public interface IProductDAO extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name = ?1")
    Product getByName(String productName);
}
