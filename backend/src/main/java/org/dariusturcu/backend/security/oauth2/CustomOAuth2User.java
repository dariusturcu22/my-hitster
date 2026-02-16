package org.dariusturcu.backend.security.oauth2;

import org.dariusturcu.backend.model.user.User;
import org.dariusturcu.backend.security.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private UserPrincipal userPrincipal;
    private Map<String, Object> attributes;

    public CustomOAuth2User(User user, Map<String, Object> attributes) {
        this.userPrincipal = new UserPrincipal(user);
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userPrincipal.getAuthorities();
    }

    @Override
    public String getName() {
        return userPrincipal.getUsername();
    }

    public User getUser() {
        return userPrincipal.getUser();
    }
}
