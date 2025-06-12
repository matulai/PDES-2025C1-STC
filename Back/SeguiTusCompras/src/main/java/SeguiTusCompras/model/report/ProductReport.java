package SeguiTusCompras.model.report;

import SeguiTusCompras.model.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductReport extends Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long purchaseCount;

    private Long favoritesCount;

    @OneToOne 
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

     public ProductReport(){
        this.purchaseCount = 0L;
        this.favoritesCount = 0L;
    }

    @Override
    public void addPurchase() {
       this.purchaseCount++;
    }

    public void decreaseFavorite() { this.favoritesCount--; }

    public void addFavorite() {
        this.favoritesCount++;
    }

}
