package by.bsuir.bankcreditaccounting.controller;

import by.bsuir.bankcreditaccounting.dto.UserRegistrationDto;
import by.bsuir.bankcreditaccounting.dto.UserResponseDto;
import by.bsuir.bankcreditaccounting.mapper.UserMapper;
import by.bsuir.bankcreditaccounting.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRegistrationDto userDto) {
        return new ResponseEntity<>(
                UserMapper.entityToDto(authenticationService.saveUser(userDto)),
                HttpStatus.OK
        );
    }
}
