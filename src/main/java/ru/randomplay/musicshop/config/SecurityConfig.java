package ru.randomplay.musicshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        // Разрешаем данные эндпоинты для всех
                        .requestMatchers("/login", "/registration", "/home", "/product/{id}",
                                "/styles/**", "/scripts/**", "/icons/**", "/images/**").permitAll()
                        // Данные эндпоинты требуют специальной роли
                        .requestMatchers("/employee/**").hasRole("EMPLOYEE")
                        .requestMatchers("/warehouse-manager/**").hasRole("WAREHOUSE_MANAGER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Все остальные эндпоинты требуют аутентификации
                        .anyRequest().authenticated())
                // Настройка кастомной страницы для входа в аккаунт
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/login-success", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                // Настройка выхода из аккаунта
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                        .permitAll())
                // Настраиваем csrf (достаточно использовать thymeleaf в формах - th:action и "_csrf" автоматически вставится)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/logout"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
