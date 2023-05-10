package by.bsuir.bankcreditaccounting.security.jwt.mapper;

import by.bsuir.bankcreditaccounting.domain.Role;
import by.bsuir.bankcreditaccounting.domain.User;
import by.bsuir.bankcreditaccounting.security.jwt.JwtUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserMapper {
    private JwtUserMapper() {}

    public static JwtUser toJwtUser(User user) {
        return JwtUser.builder()
                .id(user.getId())
                .passwordHash(user.getPasswordHash())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .authorities(mapToGrantedAuthority(new ArrayList<>(user.getRoleList())))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(List<Role> roles) {
        return roles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
