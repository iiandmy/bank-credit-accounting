package by.bsuir.bankcreditaccounting.service;

import by.bsuir.bankcreditaccounting.domain.Credit;
import by.bsuir.bankcreditaccounting.domain.User;
import by.bsuir.bankcreditaccounting.dto.creditCreation.CreditCreationRequestDto;
import by.bsuir.bankcreditaccounting.dto.creditCreation.CreditCreationResponseDto;
import by.bsuir.bankcreditaccounting.persistance.CreditRepository;
import by.bsuir.bankcreditaccounting.persistance.StatusRepository;
import by.bsuir.bankcreditaccounting.persistance.UserRepository;
import by.bsuir.bankcreditaccounting.util.StringConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository creditRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;

    public CreditCreationResponseDto createCredit(
            CreditCreationRequestDto creditDto,
            String userEmail
    ) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Credit credit = new Credit();
        credit.setUser(user);
        credit.setRate(creditDto.getRate());
        credit.setStatus(statusRepository.findByName(StringConstants.STATUS_PENDING).get());
        try {
            credit.setStartDate(dateFormat.parse(creditDto.getStartDate()));
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
}
