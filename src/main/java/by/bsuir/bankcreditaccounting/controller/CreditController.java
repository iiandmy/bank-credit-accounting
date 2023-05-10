package by.bsuir.bankcreditaccounting.controller;

import by.bsuir.bankcreditaccounting.config.JwtTokenService;
import by.bsuir.bankcreditaccounting.dto.creditCreation.CreditCreationRequestDto;
import by.bsuir.bankcreditaccounting.dto.creditCreation.CreditCreationResponseDto;
import by.bsuir.bankcreditaccounting.service.CreditService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credit")
public class CreditController {
    private final JwtTokenService jwtTokenService;
    private final CreditService creditService;

    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        return new ResponseEntity<>("Hello, world", HttpStatus.OK);
    }

    @PostMapping("/request")
    public ResponseEntity<CreditCreationResponseDto> requestCredit(
            @RequestBody CreditCreationRequestDto creditDto,
            HttpServletRequest request
    ) {
        String authHeader = request.getHeader("Authorization");
        String accessToken = authHeader.substring(7);

        String email = jwtTokenService.parseToken(accessToken);

        return new ResponseEntity<>(creditService.createCredit(creditDto, email), HttpStatus.OK);
    }
}
