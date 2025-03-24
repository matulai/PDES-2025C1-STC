package SeguiTusCompras.persistence;

import SeguiTusCompras.model.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminDao extends JpaRepository<Admin, Long> {
}
