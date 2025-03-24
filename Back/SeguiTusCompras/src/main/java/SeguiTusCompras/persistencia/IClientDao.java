package SeguiTusCompras.persistencia;

import SeguiTusCompras.modelo.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientDao extends JpaRepository<Client, Long> {
}
