package org.dariusturcu.backend.security;

import lombok.RequiredArgsConstructor;
import org.dariusturcu.backend.exception.ResourceNotFoundException;
import org.dariusturcu.backend.model.user.User;
import org.dariusturcu.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User {" + usernameOrEmail + "} not found"));

        return new UserPrincipal(user);
    }
}
