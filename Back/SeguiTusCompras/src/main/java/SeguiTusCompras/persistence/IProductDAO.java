package SeguiTusCompras.persistence;

import SeguiTusCompras.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductDAO extends JpaRepository<Product, Long> {
}
