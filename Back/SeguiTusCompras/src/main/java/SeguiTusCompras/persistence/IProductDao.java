package SeguiTusCompras.persistence;

import SeguiTusCompras.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductDao extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name = ?1")
    Product getByName(String productName);

    @Query("SELECT p FROM Product p WHERE p.mlaId = ?1")
    Product getByMlaId(String id);

    @Query("SELECT DISTINCT p FROM User u JOIN u.favorites p")
    List<Product> findAllFavoriteProductsOfAllUsers();

    @Query("SELECT p FROM User u JOIN u.favorites p GROUP BY p ORDER BY COUNT(u) DESC")
    List<Product> findTopProductsFavoritesOfAllUsers(Pageable pageable);

    @Query("SELECT pr FROM User u JOIN u.purchases pr GROUP BY pr ORDER BY COUNT(pr) DESC")
    List<Product> findTopPurchasesProductsOfAllUsers(Pageable pageable);
}
