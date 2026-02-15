package org.dariusturcu.backend.model.mapper;

import org.dariusturcu.backend.model.user.User;
import org.dariusturcu.backend.model.user.UserDetailDTO;
import org.dariusturcu.backend.model.user.UserSummaryDTO;
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
}
