package by.bsuir.bankcreditaccounting.dto.creditCreation;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCreationRequestDto {
    private String startDate;
    private String expiringDate;
    private Double rate;
}
