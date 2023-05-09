package by.bsuir.bankcreditaccounting.controller;

import by.bsuir.bankcreditaccounting.config.JwtTokenService;
import by.bsuir.bankcreditaccounting.dto.LoginRequestDto;
import by.bsuir.bankcreditaccounting.dto.LoginResponseDto;
import by.bsuir.bankcreditaccounting.dto.UserRegistrationDto;
import by.bsuir.bankcreditaccounting.dto.UserResponseDto;
import by.bsuir.bankcreditaccounting.mapper.UserMapper;
import by.bsuir.bankcreditaccounting.security.UserDetailsEntity;
import by.bsuir.bankcreditaccounting.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRegistrationDto userDto) {
        return new ResponseEntity<>(
                UserMapper.entityToDto(authenticationService.saveUser(userDto)),
                HttpStatus.OK
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authorizeUser(@RequestBody LoginRequestDto request) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        UserDetailsEntity user = (UserDetailsEntity) authenticationService.loadUserByUsername(request.getEmail());

        LoginResponseDto response = authenticationService.loginUser(
                user,
                jwtTokenService.generateRefreshToken(user),
                jwtTokenService.generateAccessToken(user)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshTokens(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String refreshToken = authHeader.substring(7);

        String email = jwtTokenService.parseToken(refreshToken);
        UserDetailsEntity user = (UserDetailsEntity) authenticationService.loadUserByUsername(email);
        String newRefreshToken = jwtTokenService.generateRefreshToken(user);
        String newAccessToken = jwtTokenService.generateAccessToken(user);
        if (!authenticationService.updateRefreshToken(email, refreshToken, newRefreshToken)) {
            throw new RuntimeException("Error while login!");
        }
        return new ResponseEntity<>(
                LoginResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build(), HttpStatus.OK);
    }
}
