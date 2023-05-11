package by.bsuir.bankcreditaccounting.mapper;

import by.bsuir.bankcreditaccounting.domain.Credit;
import by.bsuir.bankcreditaccounting.domain.CreditPlan;
import by.bsuir.bankcreditaccounting.dto.credit.CreditResponse;
import by.bsuir.bankcreditaccounting.dto.planCreation.CreditPlanResponseDto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CreditMapper {
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
    public static CreditResponse toDto(Credit credit) {
        return CreditResponse.builder()
                .id(credit.getId())
                .plan(planToDto(credit.getCreditPlan()))
                .status(credit.getStatus().getName())
                .startDate(dateFormat.format(credit.getStartDate()))
                .endDate(dateFormat.format(credit.getExpiringDate()))
                .build();
    }

    public static CreditPlanResponseDto planToDto(CreditPlan plan) {
        return CreditPlanResponseDto.builder()
                .id(plan.getId())
                .creditAmount(plan.getCreditAmount())
                .rate(plan.getRate())
                .firstPayment(plan.getFirstPayment())
                .build();
    }
}
