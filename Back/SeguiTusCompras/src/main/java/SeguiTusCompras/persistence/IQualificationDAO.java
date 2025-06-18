package SeguiTusCompras.persistence;

import SeguiTusCompras.model.Qualification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IQualificationDAO extends JpaRepository<Qualification, Long> {

    @Query("""
       SELECT q FROM Qualification q
       """)
    Page<Qualification> findAllPage(Pageable pageable);
}
