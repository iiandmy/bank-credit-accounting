package by.bsuir.bankcreditaccounting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequestDto {
    private String email;
    private String password;
}
