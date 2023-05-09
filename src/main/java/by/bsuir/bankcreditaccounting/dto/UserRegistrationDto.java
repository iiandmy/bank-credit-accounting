package by.bsuir.bankcreditaccounting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
