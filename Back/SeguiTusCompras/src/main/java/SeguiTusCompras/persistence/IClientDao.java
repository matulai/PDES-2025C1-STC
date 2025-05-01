package SeguiTusCompras.persistence;

import SeguiTusCompras.model.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IClientDao extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE c.name = ?1")
    Client getByName(String clientName);
}
