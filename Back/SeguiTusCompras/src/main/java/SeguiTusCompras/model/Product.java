package SeguiTusCompras.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true) // solo para probar cambiar luego
    private String MLA_id;

    @Column(nullable = true) // solo para probar cambiar luego
    private String category_id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(precision = 12, scale = 3)
    private BigDecimal price;

    @Column(nullable = true) // solo para probar cambiar luego
    private String image_url;

    @Column
    private int stock;

    @Column
    private String description;

    @OneToMany(mappedBy = "product")
    private Set<Qualification> qualifications;

    public void makeQualification(Qualification qualification){
        qualifications.add(qualification);
    }


}
