package by.bsuir.bankcreditaccounting.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ba_credit_plan")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "creditPlan")
    private List<Credit> creditList;

    private Double rate;
    private Double creditAmount;
    private Double firstPayment;
}
