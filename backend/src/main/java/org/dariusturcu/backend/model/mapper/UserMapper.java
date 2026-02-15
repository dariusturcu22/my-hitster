package org.dariusturcu.backend.model.mapper;

import org.dariusturcu.backend.model.user.*;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserSummaryDTO toSummaryDTO(User user) {
        return new UserSummaryDTO(
                user.getId(),
                user.getUsername()
        );
    }

    public UserDetailDTO toDetailDTO(User user) {
        return new UserDetailDTO(

        );
    }

    public User toEntity(CreateUserRequest request) {
        return new User(

        );
    }

    public User updateEntity(User user, UpdateUserRequest request) {
        return user;
    }
}
