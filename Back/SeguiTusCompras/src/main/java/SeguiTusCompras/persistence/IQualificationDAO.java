package SeguiTusCompras.persistence;

import SeguiTusCompras.model.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IQualificationDAO extends JpaRepository<Qualification, Long> {

    @Query("""        
    SELECT q 
    FROM Qualification q 
    WHERE q.user.name = ?1 AND q.product.name = ?2"""
    )
    Qualification getQualificationFor(String userName, String productName);
}
