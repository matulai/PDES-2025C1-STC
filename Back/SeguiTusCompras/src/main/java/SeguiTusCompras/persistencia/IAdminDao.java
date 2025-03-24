package SeguiTusCompras.persistencia;

import SeguiTusCompras.modelo.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminDao extends JpaRepository<Admin, Long> {
}
