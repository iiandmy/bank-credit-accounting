package by.bsuir.bankcreditaccounting.service;

import by.bsuir.bankcreditaccounting.domain.User;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User findByEmail(String email);

    User findById(Long id);

    boolean delete(Long id);
}
