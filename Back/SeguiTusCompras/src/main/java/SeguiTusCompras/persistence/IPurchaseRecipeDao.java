package SeguiTusCompras.persistence;

import SeguiTusCompras.model.PurchaseRecipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPurchaseRecipeDao extends JpaRepository<PurchaseRecipe, Long> {
    @Query("SELECT pr FROM PurchaseRecipe pr")
    Page<PurchaseRecipe> findAllPage(Pageable pageable);
}
