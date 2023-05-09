package by.bsuir.bankcreditaccounting.service;

import by.bsuir.bankcreditaccounting.domain.RefreshToken;
import by.bsuir.bankcreditaccounting.domain.Role;
import by.bsuir.bankcreditaccounting.domain.User;
import by.bsuir.bankcreditaccounting.dto.LoginResponseDto;
import by.bsuir.bankcreditaccounting.dto.UserRegistrationDto;
import by.bsuir.bankcreditaccounting.mapper.UserMapper;
import by.bsuir.bankcreditaccounting.persistance.RefreshTokenRepository;
import by.bsuir.bankcreditaccounting.persistance.RoleRepository;
import by.bsuir.bankcreditaccounting.persistance.UserRepository;
import by.bsuir.bankcreditaccounting.security.UserDetailsEntity;
import by.bsuir.bankcreditaccounting.util.StringConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(UserRegistrationDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("User with such email is already exists");
        }
        System.out.println(dto.getPassword() + " " + dto.getEmail());
        User createdUser = userRepository.save(createUserFromRegistrationDto(dto));
        roleRepository.findByName(StringConstants.ROLE_USER).get().getUserList().add(createdUser);

        return createdUser;
    }

    public LoginResponseDto loginUser(
            UserDetailsEntity userDetails,
            String refreshToken,
            String accessToken
    ) {
        RefreshToken token = new RefreshToken();
        token.setToken(refreshToken);
        token.setUser(userDetails.getUser());

        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).get();
        user.getRefreshTokenList().add(token);

        refreshTokenRepository.save(token);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean updateRefreshToken(String email, String oldToken, String newToken) {
        User user = userRepository.findByEmail(email).orElseThrow();
        if (user.getRefreshTokenList().stream().noneMatch((token) -> token.getToken().equals(oldToken))) {
            throw new RuntimeException();
        }
        refreshTokenRepository.delete(refreshTokenRepository.findByToken(oldToken).orElseThrow());
        user.setRefreshTokenList(
                user.getRefreshTokenList().stream()
                        .filter((token) -> !token.getToken().equals(oldToken))
                        .collect(Collectors.toList())
        );

        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setToken(newToken);
        newRefreshToken.setUser(user);
        user.getRefreshTokenList().add(newRefreshToken);

        refreshTokenRepository.save(newRefreshToken);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User with such email is not found")
        );

        return UserMapper.entityToUserDetails(user);
    }

    private User createUserFromRegistrationDto(UserRegistrationDto dto) {
        Role userRole = roleRepository
                .findByName(StringConstants.ROLE_USER)
                .orElseThrow(() ->
                        new RuntimeException("Server Error! " + StringConstants.ROLE_USER + " role not found.")
                );

        return User.builder()
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .creditList(new ArrayList<>())
                .roleList(List.of(userRole))
                .refreshTokenList(new ArrayList<>())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .build();
    }
}
