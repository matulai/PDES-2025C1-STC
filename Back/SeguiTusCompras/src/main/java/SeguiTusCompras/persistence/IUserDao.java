package SeguiTusCompras.persistence;

import SeguiTusCompras.model.Product;
import SeguiTusCompras.model.Qualification;
import SeguiTusCompras.model.user.Role;
import SeguiTusCompras.model.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserDao extends JpaRepository<User, Long> {
    @Query("""
       SELECT u FROM User u WHERE u.name = ?1
       """)
    User getByName(String name);
    
    @Query("""
       SELECT u FROM User u WHERE u.role = ?1
       """)
    List<User> UsersByRole(Role role);

    @Query("""
       SELECT q FROM Qualification q WHERE q.user.name = ?1
       """)
    List<Qualification> getQualifications(String userName);

    @Query("""
            SELECT p FROM User u JOIN u.purchases p WHERE u.name = ?1
            """)
    List<Product> getPurchases(String userName);

    @Query("""
            SELECT f FROM User u JOIN u.favorites f WHERE u.name = ?1
            """)
    List<Product> getFavorites(String userName);
}
