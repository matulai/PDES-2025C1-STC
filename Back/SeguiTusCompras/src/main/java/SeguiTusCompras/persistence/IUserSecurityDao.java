package SeguiTusCompras.persistence;

import SeguiTusCompras.Security.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IUserSecurityDao extends JpaRepository<UserSecurity, Long> {

    @Query("""
       SELECT us FROM UserSecurity us
       JOIN us.user u
       WHERE u.name like ?1
       """)
    UserSecurity getByName(String name);
}

