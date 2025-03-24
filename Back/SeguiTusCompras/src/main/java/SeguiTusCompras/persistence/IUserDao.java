package SeguiTusCompras.persistence;

import SeguiTusCompras.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User, Long> {
}
