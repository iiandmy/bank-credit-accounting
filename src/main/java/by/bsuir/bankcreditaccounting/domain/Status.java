package by.bsuir.bankcreditaccounting.domain;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "ba_status")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
