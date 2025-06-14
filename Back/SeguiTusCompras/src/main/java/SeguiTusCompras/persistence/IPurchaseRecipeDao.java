package SeguiTusCompras.persistence;

import SeguiTusCompras.model.PurchaseRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseRecipeDao extends JpaRepository<PurchaseRecipe, Long> {
}
