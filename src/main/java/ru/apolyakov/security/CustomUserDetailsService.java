package ru.apolyakov.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.apolyakov.model.Role;
import ru.apolyakov.model.User;
import ru.apolyakov.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Кастомный сервис авторизации, используя Spring Data JPA
 *
 * @author apolyakov
 * @since 06.01.2019
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Получить данные авторизации о пользователе по его логину.
     * Многие параметры захардкожены: enabled, accountNonExpired, credentialsNonExpired, accountNonLocked
     *
     * @param username логин пользователя
     * @return данные авторизации пользователя
     * @throws UsernameNotFoundException пользователь не найден
     */
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return new AuthorizedUser(user, getGrantedAuthorities(user));
    }

    /**
     * Доступные для пользователя права доступа на основе ролей
     *
     * @param user пользователь, проходящий проверку прав
     * @return список прав доступа
     */
    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        // ! ADMIN has USER authority
        return authorities;
    }
}
