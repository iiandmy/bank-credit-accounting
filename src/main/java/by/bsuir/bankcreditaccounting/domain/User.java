package by.bsuir.bankcreditaccounting.domain;

import javax.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "ba_user")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Credit> creditList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ba_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roleList;

    @Column(name = "f_name")
    private String firstName;
    @Column(name = "l_name")
    private String lastName;
    private String email;
    private String passwordHash;
}
