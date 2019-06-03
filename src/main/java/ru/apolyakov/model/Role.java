package ru.apolyakov.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role  implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    NOBODY;

    @Override
    public String getAuthority() {
        return name();
    }
}