package by.bsuir.bankcreditaccounting.mapper;

import by.bsuir.bankcreditaccounting.domain.User;
import by.bsuir.bankcreditaccounting.dto.UserResponseDto;
import by.bsuir.bankcreditaccounting.security.UserDetailsEntity;

public class UserMapper {
    public static UserResponseDto entityToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public static UserDetailsEntity entityToUserDetails(User user) {
        return UserDetailsEntity.builder()
                .user(user)
                .build();
    }
}
