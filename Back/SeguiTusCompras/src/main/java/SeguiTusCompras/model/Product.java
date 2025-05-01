package SeguiTusCompras.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String MLA_id;

    @Column(nullable = false)
    private String category_id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(precision = 12, scale = 3)
    private BigDecimal price;

    @Column(nullable = false)
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
