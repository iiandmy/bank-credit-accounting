package by.bsuir.bankcreditaccounting.controller;

import by.bsuir.bankcreditaccounting.domain.User;
import by.bsuir.bankcreditaccounting.dto.LoginRequestDto;
import by.bsuir.bankcreditaccounting.dto.UserRegistrationDto;
import by.bsuir.bankcreditaccounting.dto.UserResponseDto;
import by.bsuir.bankcreditaccounting.mapper.UserMapper;
import by.bsuir.bankcreditaccounting.security.jwt.JwtTokenProvider;
import by.bsuir.bankcreditaccounting.service.UserService;
import by.bsuir.bankcreditaccounting.util.StringConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginRequestDto requestDto) {
        try {
            String username = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByEmail(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoleList());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            if (user.getRoleList().stream()
                    .anyMatch(role -> role.getName().equals(StringConstants.ROLE_ADMIN))) {
                response.put("is_admin", true);
            }

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRegistrationDto registrationDto) {
        return new ResponseEntity<>(
                UserMapper.entityToDto(userService.register(User.builder()
                        .firstName(registrationDto.getFirstName())
                        .lastName(registrationDto.getLastName())
                        .email(registrationDto.getEmail())
                        .passwordHash(registrationDto.getPassword())
                        .creditList(new ArrayList<>())
                .build()
        )), HttpStatus.CREATED);
    }
}
