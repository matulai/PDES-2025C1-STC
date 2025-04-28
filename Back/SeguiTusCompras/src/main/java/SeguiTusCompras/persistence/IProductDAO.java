package SeguiTusCompras.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import SeguiTusCompras.model.Product.Product;

public interface IProductDAO extends JpaRepository<Product, Long> {
}
