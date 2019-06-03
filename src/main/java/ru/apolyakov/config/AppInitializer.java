package ru.apolyakov.config;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.apolyakov.model.Role;
import ru.apolyakov.model.User;
import ru.apolyakov.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * Временный инициализатор для создания пользователя admin с заданными правами
 *
 * @author apolyakov
 * @since 06.01.2019
 */
@Component
public class AppInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private boolean alreadySetup = false;
    private PasswordEncoder encoder;

    @Autowired
    public AppInitializer(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.encoder = passwordEncoder;
    }


    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) {
            return;
        }

        Role adminRole = Role.ROLE_ADMIN;
        Role userRole = Role.ROLE_USER;
        CheckForAdminUser(Sets.newHashSet(adminRole, userRole));
        alreadySetup = true;
    }

    private void CheckForAdminUser(Set<Role> roles) {

        User adminUser = userRepository.getByLogin("admin");

        if (adminUser == null) {
            adminUser = new User();
            adminUser.setName("admin");
            adminUser.setLogin("admin");
            adminUser.setFirstName("Admin_FirstName");
            adminUser.setLastName("Admin_LasName");
            adminUser.setEmail("admin@gmail.com");
            adminUser.setEnabled(true);
            adminUser.setPassword(encoder.encode("admin"));
            adminUser.setRoles(roles);
            userRepository.save(adminUser);
        }
    }
}
