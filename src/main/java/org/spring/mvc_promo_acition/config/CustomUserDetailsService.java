package org.spring.mvc_promo_acition.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.mvc_promo_acition.model.entiies.Admin;
import org.spring.mvc_promo_acition.repositories.AdminRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Аннотация @Service указывает, что этот класс является сервисом, который будет управляться Spring
@Service
@RequiredArgsConstructor // Lombok-аннотация для автоматической генерации конструктора с final полями
@Slf4j // Lombok-аннотация для добавления логирования (логгер SLF4J)
public class CustomUserDetailsService implements UserDetailsService {

    //для преобразования пароля (сам бин находиться в конфигах)
    //private final PasswordEncoder passwordEncoder;

    // Репозиторий для работы с сущностью Admin
    private final AdminRepository adminRepository;

    // Префикс для ролей, который используется в Spring Security (например, ROLE_ADMIN)
    private final static String ROLE_PREFIX = "ROLE_";

    // Переопределенный метод из интерфейса UserDetailsService, который загружает пользователя по имени
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Получаем пользователя по имени пользователя из базы данных
        Optional<Admin> userOptional = adminRepository.findByUsername(username);

        // Если пользователь не найден, выбрасываем исключение UsernameNotFoundException
        Admin admin = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("User Not Found with username: " + username));
        
        // Логируем информацию о найденном пользователе для отладки
        log.info("[CustomUserDetailsService] user is {}", admin);


        // Извлекаем пароль пользователя
        String password = admin.getPassword();

        // Формируем роль с префиксом (например, ROLE_ADMIN)
        String role = ROLE_PREFIX + admin.getRole().toString();

        // Создаем список ролей (GrantedAuthority), в данном случае с одной ролью
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role));

        // Возвращаем объект CustomUserDetails, который используется Spring Security для аутентификации
        CustomUserDetails customUserDetails = new  CustomUserDetails(username, password, roles);
        log.info("[CustomUserDetails] user is {}", customUserDetails.getAuthorities());
        return customUserDetails;
    }
}
