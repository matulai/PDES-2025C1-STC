package SeguiTusCompras.persistence;

import SeguiTusCompras.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserDao extends JpaRepository<User, Long> {
    @Query
            ("""
             SELECT * FROM USER WHERE USER.name = ?1
             """)
    User getByName(String name);
}
