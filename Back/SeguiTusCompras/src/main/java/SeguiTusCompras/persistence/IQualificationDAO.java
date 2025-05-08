package SeguiTusCompras.persistence;

import SeguiTusCompras.model.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQualificationDAO extends JpaRepository<Qualification, Long> {
}
