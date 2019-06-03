package ru.apolyakov.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(
            RestAuthenticationEntryPoint restAuthenticationEntryPoint,
            @Qualifier("customUserDetailsService") UserDetailsService userDetailsService) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CustomSavedRequestAwareAuthenticationSuccessHandler customSuccessHandler() {
        return new CustomSavedRequestAwareAuthenticationSuccessHandler();
    }

    // return 401 instead 302
    @Bean
    public SimpleUrlAuthenticationFailureHandler customFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable() // !!! enable in production mode
                // обрабатываем исключения, для обработки ошибок используется restAuthenticationEntryPoint
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                // настройка мэппингов, требующих авторизацию
                .authorizeRequests()
                //.antMatchers("/api").authenticated()
                .antMatchers("/api/menuItems/**").hasRole("ADMIN")
                .antMatchers("/api/restaurants/**").hasRole("ADMIN")
                .antMatchers("/api/menus/**").hasRole("ADMIN")
                .antMatchers("/api/vote/**").hasRole("USER")
                .antMatchers("/api/votes/**").hasRole("NOBODY")

                .and()
                // authentication processing filter: org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
                // formLogin create this filter and provide additional methods (onSuccess and onFailure)
                .formLogin()
                .successHandler(customSuccessHandler())
                .failureHandler(customFailureHandler())
                .and()
                .logout();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
