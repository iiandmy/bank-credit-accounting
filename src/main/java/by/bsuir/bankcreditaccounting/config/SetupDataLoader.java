package by.bsuir.bankcreditaccounting.config;

import by.bsuir.bankcreditaccounting.domain.Role;
import by.bsuir.bankcreditaccounting.domain.User;
import by.bsuir.bankcreditaccounting.persistance.RoleRepository;
import by.bsuir.bankcreditaccounting.persistance.UserRepository;
import by.bsuir.bankcreditaccounting.util.StringConstants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final String STANDARD_ADMIN_EMAIL = "admin@gmail.ru";
    private final String STANDARD_ADMIN_PASSWORD = "qwerty123";
    private final String STANDARD_ADMIN_FIRSTNAME = "John";
    private final String STANDARD_ADMIN_LASTNAME = "Doe";

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Role adminRole = createRoleIfNotFound(StringConstants.ROLE_ADMIN);
        Role userRole = createRoleIfNotFound(StringConstants.ROLE_USER);

        User admin = createAdminIfNotFound(List.of(adminRole, userRole));
        adminRole.getUserList().add(admin);
        userRole.getUserList().add(admin);
    }

    private User createAdminIfNotFound(List<Role> adminRoleList) {
        return userRepository.findByEmail(STANDARD_ADMIN_EMAIL)
                .orElseGet(() -> userRepository.save(createUser(
                        STANDARD_ADMIN_EMAIL,
                        STANDARD_ADMIN_FIRSTNAME,
                        STANDARD_ADMIN_LASTNAME,
                        STANDARD_ADMIN_PASSWORD,
                        adminRoleList
                )));
    }

    private Role createRoleIfNotFound(String roleName) {
        return roleRepository
                .findByName(roleName)
                .orElseGet(() -> roleRepository.save(createRole(roleName)));
    }

    private Role createRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        role.setUserList(new HashSet<>());
        return role;
    }

    private User createUser(
        String email,
        String firstName,
        String lastName,
        String password,
        List<Role> roleList
    ) {
        return User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .roleList(roleList)
                .passwordHash(passwordEncoder.encode(password))
                .creditList(new ArrayList<>())
                .build();
    }
}
