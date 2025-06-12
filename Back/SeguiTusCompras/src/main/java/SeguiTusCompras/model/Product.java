package SeguiTusCompras.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import SeguiTusCompras.model.report.ProductReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mlaId;

    @Column(nullable = false)
    private String name;

    @Column(precision = 12, scale = 3)
    private BigDecimal price;

    @Column(nullable = false)
    private String imageURL;

    @Column
    private String domainId;

    @Column
    private String description;

    @OneToMany(mappedBy = "product")
    private List<Qualification> qualifications;

    @OneToOne(mappedBy = "product", fetch = FetchType.EAGER)
    private ProductReport productReport;

    public void increaseFavoritesCounter() {
        this.getProductReport().addFavorite();
    }

    public void decreaseFavoritesCounter() {
        this.getProductReport().decreaseFavorite();
    }

    public void increasePurchasesCounter() {
        this.getProductReport().addPurchase();
    }
}
