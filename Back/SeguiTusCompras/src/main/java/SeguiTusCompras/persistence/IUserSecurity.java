package SeguiTusCompras.persistence;

import SeguiTusCompras.Security.UserSecurity;
import SeguiTusCompras.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserSecurity extends JpaRepository<UserSecurity, Long> {

    @Query("""
       SELECT us FROM UserSecurity us
       JOIN us.userModel u
       WHERE u.name = ?1
       """)
    UserSecurity getByName(String name);
}

