package org.dariusturcu.backend.security.util;

import org.dariusturcu.backend.model.user.User;
import org.dariusturcu.backend.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authentication user found");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getUser();
        }

        throw new RuntimeException("Invalid principal type");
        // TODO understand and create error
    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static boolean isCurrentUser(Long userId) {
        return getCurrentUserId().equals(userId);
    }
}
