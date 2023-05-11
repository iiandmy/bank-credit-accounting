package by.bsuir.bankcreditaccounting.dto.credit;

import by.bsuir.bankcreditaccounting.dto.planCreation.CreditPlanResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreditResponse {
    private Long id;
    private CreditPlanResponseDto plan;
    private String status;
    private String startDate;
    private String endDate;
}
