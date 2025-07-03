package SeguiTusCompras.persistence;

import SeguiTusCompras.model.PurchaseRecipe;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

public interface IUserDao extends JpaRepository<User, Long> {
    @Query("""
       SELECT u FROM User u WHERE u.name = ?1
       """)
    User getByName(String name);
    
    @Query("""
       SELECT u FROM User u WHERE u.role = ?1
       """)
    Page<User> UsersByRole(Role role, Pageable pageable);

    @Query("""
            SELECT pr FROM User u JOIN u.purchases pr WHERE u.name = ?1
            """)
    Page<PurchaseRecipe> getPurchasesPage(String userName, Pageable pageable);

    @Query("""
            SELECT f FROM User u JOIN u.favorites f WHERE u.name = ?1
            """)
    Page<Product> getFavoritesPage(String userName, Pageable pageable);

    @Query("SELECT u FROM User u LEFT JOIN u.purchases p WHERE u.role = ?1 GROUP BY u ORDER BY COUNT(p) DESC")
    Page<User> findTopClientsByPurchases(Role role, Pageable pageable);
}
