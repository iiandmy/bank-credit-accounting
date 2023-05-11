package by.bsuir.bankcreditaccounting.dto.planCreation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreditPlanResponseDto {
    private Long id;
    private Double rate;
    private Double creditAmount;
    private Double firstPayment;
}
