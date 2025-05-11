package SeguiTusCompras.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;

    @OneToOne
    @JoinColumn(name = "qualification_id")
    private Qualification qualification;

    public Comment(String comment, Qualification qualification) {
        this.comment = comment;
        this.qualification = qualification;
    }
}
