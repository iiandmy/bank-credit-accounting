package by.bsuir.bankcreditaccounting.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ba_credit")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne
    private Status status;

    private Date expiringDate;
    private Date startDate;
    private Double rate;
}
