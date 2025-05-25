package SeguiTusCompras.model;

import SeguiTusCompras.model.user.User;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Qualification {

    public Qualification(User user, Integer score, Product product) {
        this.user = user;
        this.product = product;
        this.score = score;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qualification_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "client_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer score;

    @OneToOne(mappedBy = "qualification", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Comment comment;
}
