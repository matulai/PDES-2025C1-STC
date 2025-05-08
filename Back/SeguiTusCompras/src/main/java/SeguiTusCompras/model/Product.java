package SeguiTusCompras.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mlaId;

    @Column(nullable = false, unique = true)
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
    private Set<Qualification> qualifications;

    public void makeQualification(Qualification qualification){
        qualifications.add(qualification);
    }

}
