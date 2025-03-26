package SeguiTusCompras.model;

import SeguiTusCompras.model.user.Client;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Dictionary;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String MLA_id;

    @Column(nullable = false)
    private String category_id;

    @Column(nullable = false)
    private String name;

    @Column(precision = 12, scale = 3)
    private BigDecimal price;

    @Column(nullable = false)
    private String image_url;

    @Column
    private int stock;

    @Column
    private String description;

    @Column
    Dictionary<Long, Integer> Qualifications; // Key: ClientId - Value: Score

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public void receiveQualification(Integer score, Client user){
        this.Qualifications.put(user.getId(), score);
        user.qualificationAdded(this, score);
    }

}
