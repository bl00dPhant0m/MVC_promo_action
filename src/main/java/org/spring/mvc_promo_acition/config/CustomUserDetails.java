package org.spring.mvc_promo_acition.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Реализация интерфейса UserDetails, предоставляющего информацию о пользователе для Spring Security
public class CustomUserDetails implements UserDetails {
    // Имя пользователя
    private final String username;
    // Пароль пользователя
    private final String password;
    // Список ролей или прав пользователя
    private final List<GrantedAuthority> roles;

    // Конструктор для инициализации полей пользователя
    public CustomUserDetails(String username, String password, List<GrantedAuthority> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    // Возвращает коллекцию прав (ролей) пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }
    // Возвращает пароль пользователя
    @Override
    public String getPassword() {
        return this.password;
    }
    // Возвращает имя пользователя (логин)
    @Override
    public String getUsername() {
        return this.username;
    }

    // Учетная запись активна, если срок действия не истек (true в данном случае)
    @Override
    public boolean isAccountNonExpired() {
        return true; // Учетная запись не истекла
    }

    // Учетная запись не заблокирована (true в данном случае)
    @Override
    public boolean isAccountNonLocked() {
        return true; // Учетная запись не заблокирована
    }

    // Учетные данные (пароль) не истекли (true в данном случае)
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Пароль не истек
    }

    // Учетная запись активна (true в данном случае)
    @Override
    public boolean isEnabled() {
        return true; // Учетная запись активна
    }
}
