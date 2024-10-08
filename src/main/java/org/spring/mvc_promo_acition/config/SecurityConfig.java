package org.spring.mvc_promo_acition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Указывает, что этот класс содержит конфигурацию Spring
public class SecurityConfig {

    /**
     * Конфигурирует безопасность Spring для приложения.
     *
     * @param http Объект HttpSecurity, используемый для конфигурации фильтров безопасности.
     * @return Конфигурированный SecurityFilterChain.
     * @throws Exception Если возникает проблема при конфигурации безопасности.
     */
    @Bean
    // Определяет этот метод как бин, который будет добавлен в контекст приложения
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // Определение правил авторизации URL
        http
                .authorizeHttpRequests(auth -> auth
                        // Открытые маршруты (доступны без авторизации)
                        .requestMatchers(HttpMethod.GET,"/", "/promo-code", "/prizes", "/promo-form", "/form-for-winners", "/info", "/rules").permitAll()
                        .requestMatchers(HttpMethod.POST, "/add-admin").permitAll()

                        // Разрешаем доступ к статическим ресурсам
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()

                        // Закрытые маршруты (требуют авторизации)
                        .requestMatchers(HttpMethod.GET,  "/winners","/admin_panel").authenticated()
                        .requestMatchers(HttpMethod.POST, "/edit_status", "/form-for-winners").authenticated()

                        .requestMatchers(HttpMethod.GET,"/add_prize").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/add_prize").hasAuthority("ADMIN")
                        //.anyRequest().permitAll()
                );


        // Отключение некоторых функций безопасности (только для разработки)
        http.cors(AbstractHttpConfigurer::disable); // Отключение настройки CORS
        http.csrf(AbstractHttpConfigurer::disable); // Отключение защиты от CSRF(подмена email итп) уже вкл с версии 4
        http.headers(AbstractHttpConfigurer::disable); // Отключение настройки заголовков
        

        // Конфигурация страницы входа
        http.formLogin(login -> login
                .loginPage("/admin/authorization") // Страница входа
                .defaultSuccessUrl("/admin_panel") // URL после успешного входа
                .failureUrl("/admin/login?error=true") // URL при неудачной попытке входа
                .permitAll() // Разрешить доступ к странице входа всем
        );

        // Конфигурация выхода из системы
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/admin/authorization") // URL после успешного выхода
                .permitAll() // Разрешить доступ к выходу из системы всем
        );




        // Включение базовой аутентификации
        http.httpBasic(Customizer.withDefaults()); // Использование базовой HTTP-аутентификации по умолчанию

        return http.build(); // Построение и возвращение конфигурированного SecurityFilterChain
    }

    /**
     * Создает бин для кодировщика паролей.
     *
     * @return NoOpPasswordEncoder экземпляр для демонстрационных целей (замените на более безопасный кодировщик в продакшене).
     */

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Возвращает кодировщик паролей
    }


    @Bean
    AuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
