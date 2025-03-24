package SeguiTusCompras.persistencia;

import SeguiTusCompras.modelo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User, Long> {
}
