package ru.apolyakov.security;

import org.springframework.security.core.GrantedAuthority;
import ru.apolyakov.model.User;

import java.util.Collection;
import java.util.List;

public class AuthorizedUser  extends org.springframework.security.core.userdetails.User {

    public AuthorizedUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    private static final long serialVersionUID = 1L;

    private User user;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true,
                user.getRoles());
        this.user = user;
    }

    public AuthorizedUser(User user, List<GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true,
                authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return user.getId();
    }

    @Override
    public String toString() {
        return "AuthorizedUser{" +
                "user=" + user +
                '}';
    }
}
