package SeguiTusCompras.persistence;

import SeguiTusCompras.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserDao extends JpaRepository<User, Long> {
    @Query("""
       SELECT u FROM User u WHERE u.name = ?1
       """)
    User getByName(String name);
}
