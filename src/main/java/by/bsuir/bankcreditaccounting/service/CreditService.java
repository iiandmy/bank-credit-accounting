package by.bsuir.bankcreditaccounting.service;

import by.bsuir.bankcreditaccounting.domain.Credit;
import by.bsuir.bankcreditaccounting.domain.CreditPlan;
import by.bsuir.bankcreditaccounting.domain.Status;
import by.bsuir.bankcreditaccounting.domain.User;
import by.bsuir.bankcreditaccounting.dto.creditCreation.CreditCreationRequestDto;
import by.bsuir.bankcreditaccounting.dto.creditCreation.CreditCreationResponseDto;
import by.bsuir.bankcreditaccounting.persistance.CreditPlanRepository;
import by.bsuir.bankcreditaccounting.persistance.CreditRepository;
import by.bsuir.bankcreditaccounting.persistance.StatusRepository;
import by.bsuir.bankcreditaccounting.util.StringConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository creditRepository;
    private final CreditPlanRepository creditPlanRepository;
    private final StatusRepository statusRepository;

    public CreditCreationResponseDto createCredit(
            CreditCreationRequestDto creditDto,
            User user
    ) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

        CreditPlan creditPlan = creditPlanRepository.findById(creditDto.getCreditPlanId()).orElseThrow();

        Credit credit = new Credit();
        credit.setUser(user);
        credit.setCreditPlan(creditPlan);
        credit.setStatus(statusRepository.findByName(StringConstants.STATUS_PENDING).get());
        try {
            credit.setStartDate(new Date());
            credit.setExpiringDate(dateFormat.parse(creditDto.getExpiringDate()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        credit = creditRepository.save(credit);

        user.getCreditList().add(credit);
        return CreditCreationResponseDto.builder()
                .message("Credit application created successfully! Credit id: " + credit.getId() + ".")
                .creditId(credit.getId())
                .build();
    }

    public List<Credit> getAllCreditsByStatus(String statusName) {
        return creditRepository
                .findAllByStatusIs(
                        statusRepository.findByName(statusName).get()
                ).orElse(new ArrayList<>());
    }

    public Credit changeCreditStatus(Long creditId, Long newStatusId) {
        Status newStatus = statusRepository.findById(newStatusId).orElseThrow();
        Credit credit = creditRepository.findById(creditId).orElseThrow();

        credit.setStatus(newStatus);
        creditRepository.save(credit);

        return credit;
    }

    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    public List<CreditPlan> getAllCreditPlans() {
        return creditPlanRepository.findAll();
    }

    public CreditPlan createCreditPlan(CreditPlan plan) {
        return creditPlanRepository.save(plan);
    }
}
