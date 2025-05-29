package SeguiTusCompras.model.report;


import SeguiTusCompras.model.user.User;
import jakarta.persistence.Column;
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
public class UserReport extends Report{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userReport_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private Long numberOfPurchases;

    public UserReport() {
        this.numberOfPurchases = 0L;
    }

    @Override
    public void addPurchase() {
        numberOfPurchases++;
    }

}
