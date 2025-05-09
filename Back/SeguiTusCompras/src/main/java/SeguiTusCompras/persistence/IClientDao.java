package SeguiTusCompras.persistence;

import SeguiTusCompras.model.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientDao extends JpaRepository<Client, Long> {
}
