package by.bsuir.bankcreditaccounting.service.impl;

import by.bsuir.bankcreditaccounting.domain.Role;
import by.bsuir.bankcreditaccounting.domain.User;
import by.bsuir.bankcreditaccounting.persistance.RoleRepository;
import by.bsuir.bankcreditaccounting.persistance.UserRepository;
import by.bsuir.bankcreditaccounting.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException();
        }
        Role roleUser = roleRepository.findByName("ROLE_USER").orElseThrow();
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRoleList(userRoles);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByEmail(String email) {
        User result = userRepository.findByEmail(email).orElseThrow();
        log.info("IN findByUsername - user: {} found by email: {}", result, email);
        return result;
    }

    @Override
    public User findById(Long id) {

        User result = userRepository.findById(id).orElse(null);
        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public boolean delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
        return true;
    }
}
