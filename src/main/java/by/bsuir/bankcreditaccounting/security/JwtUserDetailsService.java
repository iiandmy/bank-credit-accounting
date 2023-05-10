package by.bsuir.bankcreditaccounting.security;

import by.bsuir.bankcreditaccounting.domain.User;
import by.bsuir.bankcreditaccounting.security.jwt.JwtUser;
import by.bsuir.bankcreditaccounting.security.jwt.mapper.JwtUserMapper;
import by.bsuir.bankcreditaccounting.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User with email: " + email + " not found.");
        }

        JwtUser jwtUser = JwtUserMapper.toJwtUser(user);
        log.info("IN loadUserByUsername - user with email: {} successfully loaded", email);
        return jwtUser;
    }
}
