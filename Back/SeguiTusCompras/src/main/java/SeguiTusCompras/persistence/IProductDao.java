package SeguiTusCompras.persistence;

import SeguiTusCompras.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IProductDao extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name = ?1")
    Product getByName(String productName);

    @Query("SELECT p FROM Product p WHERE p.mlaId = ?1")
    Optional<Product> findByMlaId(String id);

    @Query("SELECT DISTINCT p FROM User u JOIN u.favorites p")
    List<Product> findAllFavoriteProductsOfAllUsers();

    @Query("SELECT p FROM User u JOIN u.favorites p GROUP BY p ORDER BY COUNT(u) DESC")
    Page<Product> findTopProductsFavoritesOfAllUsers(Pageable pageable);

    @Query("SELECT p FROM PurchaseRecipe pr JOIN pr.purchaseProducts p GROUP BY p ORDER BY COUNT(p) DESC")
    Page<Product> findTopSellingProducts(Pageable pageable);
}
