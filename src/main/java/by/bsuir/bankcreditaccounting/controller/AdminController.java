package by.bsuir.bankcreditaccounting.controller;

import by.bsuir.bankcreditaccounting.domain.Credit;
import by.bsuir.bankcreditaccounting.domain.CreditPlan;
import by.bsuir.bankcreditaccounting.dto.credit.CreditResponse;
import by.bsuir.bankcreditaccounting.dto.planCreation.CreditPlanCreateDto;
import by.bsuir.bankcreditaccounting.dto.planCreation.CreditPlanResponseDto;
import by.bsuir.bankcreditaccounting.mapper.CreditMapper;
import by.bsuir.bankcreditaccounting.service.CreditService;
import by.bsuir.bankcreditaccounting.util.StringConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final CreditService creditService;

    @PostMapping("/credit_plan")
    public ResponseEntity<CreditPlanResponseDto> createPlan(@RequestBody CreditPlanCreateDto dto) {
        CreditPlan plan = CreditPlan.builder()
                .creditAmount(dto.getCreditAmount())
                .rate(dto.getRate())
                .firstPayment(dto.getFirstPayment())
                .build();

        return new ResponseEntity<>(
                CreditMapper.planToDto(creditService.createCreditPlan(plan)),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/status/{creditId}/{statusId}")
    public ResponseEntity<CreditResponse> changeCreditStatus(
        @PathVariable Long creditId,
        @PathVariable Long statusId
    ) {
        Credit credit = creditService.changeCreditStatus(creditId, statusId);

        return new ResponseEntity<>(
                CreditMapper.toDto(credit), HttpStatus.OK
        );
    }

    @GetMapping("/pending")
    public ResponseEntity<List<CreditResponse>> getAllPendingCredits() {
        List<Credit> creditList = creditService.getAllCreditsByStatus(StringConstants.STATUS_PENDING);

        return new ResponseEntity<>(
                creditList.stream()
                        .map(CreditMapper::toDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/active")
    public ResponseEntity<List<CreditResponse>> getAllActiveCredits() {
        List<Credit> creditList = creditService.getAllCreditsByStatus(StringConstants.STATUS_ACTIVE);

        return new ResponseEntity<>(
                creditList.stream()
                        .map(CreditMapper::toDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/paid")
    public ResponseEntity<List<CreditResponse>> getAllPaidCredits() {
        List<Credit> creditList = creditService.getAllCreditsByStatus(StringConstants.STATUS_PAID);

        return new ResponseEntity<>(
                creditList.stream()
                        .map(CreditMapper::toDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }
}
