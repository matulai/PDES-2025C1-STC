package SeguiTusCompras.model;

import SeguiTusCompras.model.user.User;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PurchaseRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(precision = 12, scale = 3)
    private BigDecimal purchasePrice;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime purchaseDate;

    @ManyToMany
    @JoinTable(
            name = "purchase_recipe_products",
            joinColumns = @JoinColumn(name = "purchase_recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> purchaseProducts = new ArrayList<>();

    public PurchaseRecipe(List<Product> purchaseProducts, User user) {
        this.user = user;
        this.purchasePrice = getPurchaseRecipePrice(purchaseProducts);
        this.purchaseProducts = new ArrayList<>(purchaseProducts);
    }

    private BigDecimal getPurchaseRecipePrice(List<Product> products) {
        BigDecimal purchasePrice = BigDecimal.ZERO;
        for (Product product : products) {
            purchasePrice = purchasePrice.add(product.getPrice());
        }
        return purchasePrice;
    }
}
