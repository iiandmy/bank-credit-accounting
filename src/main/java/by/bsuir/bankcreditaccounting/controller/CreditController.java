package by.bsuir.bankcreditaccounting.controller;

import by.bsuir.bankcreditaccounting.domain.Credit;
import by.bsuir.bankcreditaccounting.domain.User;
import by.bsuir.bankcreditaccounting.dto.StatusResponseDto;
import by.bsuir.bankcreditaccounting.dto.credit.CreditResponse;
import by.bsuir.bankcreditaccounting.dto.creditCreation.CreditCreationRequestDto;
import by.bsuir.bankcreditaccounting.dto.creditCreation.CreditCreationResponseDto;
import by.bsuir.bankcreditaccounting.dto.planCreation.CreditPlanResponseDto;
import by.bsuir.bankcreditaccounting.mapper.CreditMapper;
import by.bsuir.bankcreditaccounting.mapper.StatusMapper;
import by.bsuir.bankcreditaccounting.security.jwt.JwtTokenProvider;
import by.bsuir.bankcreditaccounting.service.CreditService;
import javax.servlet.http.HttpServletRequest;

import by.bsuir.bankcreditaccounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/credit")
public class CreditController {
    private final CreditService creditService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/request")
    public ResponseEntity<CreditCreationResponseDto> requestCredit(
            @RequestBody CreditCreationRequestDto creditDto,
            HttpServletRequest request
    ) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);

        User user = userService.findByEmail(jwtTokenProvider.getUsername(token));

        return new ResponseEntity<>(creditService.createCredit(creditDto, user), HttpStatus.OK);
    }

    @GetMapping("/plans")
    public ResponseEntity<List<CreditPlanResponseDto>> getCreditPlans() {
        return new ResponseEntity<>(
                creditService.getAllCreditPlans().stream()
                        .map(CreditMapper::planToDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @PatchMapping("/pay/{creditId}")
    public ResponseEntity<CreditResponse> changeCreditStatus(
            @PathVariable Long creditId
    ) {
        Credit credit = creditService.changeCreditStatus(creditId, 3L);

        return new ResponseEntity<>(
                CreditMapper.toDto(credit), HttpStatus.OK
        );
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<StatusResponseDto>> getStatuses() {
        return new ResponseEntity<>(
                creditService.getAllStatuses().stream()
                        .map(StatusMapper::toDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping()
    public ResponseEntity<List<CreditResponse>> getMyCredits(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);

        User user = userService.findByEmail(jwtTokenProvider.getUsername(token));

        return new ResponseEntity<>(
                user.getCreditList().stream()
                        .map(CreditMapper::toDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditResponse> getCredit(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);

        User user = userService.findByEmail(jwtTokenProvider.getUsername(token));

        return new ResponseEntity<>(
                CreditMapper.toDto(user.getCreditList().stream()
                        .filter(credit -> credit.getId().equals(id))
                        .findFirst()
                        .orElseThrow()),
                HttpStatus.OK
        );
    }
}
