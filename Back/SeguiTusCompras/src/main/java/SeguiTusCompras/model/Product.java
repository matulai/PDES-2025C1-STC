package SeguiTusCompras.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
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

    @Column(nullable = false, unique = true)
    private String mlaId;

    @Column(nullable = false)
    private String name;

    @Column(precision = 12, scale = 3)
    private BigDecimal price;

    @Column(nullable = false)
    private String imageURL;

    @Column
    private String domainId;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "product")
    private List<Qualification> qualifications;
}
