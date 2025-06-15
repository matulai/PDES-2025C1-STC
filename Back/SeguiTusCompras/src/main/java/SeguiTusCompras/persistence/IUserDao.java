package SeguiTusCompras.persistence;

import SeguiTusCompras.model.PurchaseRecipe;
import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Qualification;
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
       SELECT q FROM Qualification q WHERE q.user.name = ?1
       """)
    List<Qualification> getQualifications(String userName);

    @Query("""
            SELECT pr FROM User u JOIN u.purchases pr WHERE u.name = ?1
            """)
    List<PurchaseRecipe> getPurchases(String userName);

    @Query("""
            SELECT f FROM User u JOIN u.favorites f WHERE u.name = ?1
            """)
    List<Product> getFavorites(String userName);

    @Query("SELECT u FROM User u LEFT JOIN u.purchases p GROUP BY u ORDER BY COUNT(p) DESC")
    List<User> findTopUsersWithMostPurchasesOfAllUsers(Pageable pageable);
}
