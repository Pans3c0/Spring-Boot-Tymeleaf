package com.pacheco.gestiontareas.shared.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

/**
 * Configuración de Spring Security.
 * Define las reglas de autorización, la página de login personalizada
 * y los permisos para recursos estáticos y la consola H2.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Reglas de autorización
        http.authorizeHttpRequests(requests ->
                requests.requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/login", "/logout", "/auth/register", "/auth/register/submit", "/h2-console/**", "/img/**", "/css/**").permitAll()
                        .anyRequest().authenticated());

        // Página de login personalizada
        http.formLogin(login -> {
            login.loginPage("/login")
                    .defaultSuccessUrl("/")
                    .permitAll();
        });

        // Evitar el parámetro "continue" en la URL después del login
        http.requestCache(cache -> {
            HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
            requestCache.setMatchingRequestParameterName(null);
            cache.requestCache(requestCache);
        });

        // Logout por defecto de Spring Security
        http.logout(Customizer.withDefaults());

        // Desactivar CSRF para la consola H2 (solo desarrollo)
        http.csrf((csrf) -> {
            csrf.ignoringRequestMatchers("/h2-console/**");
        });

        // Permitir frames para la consola H2
        http.headers((headers) ->
                headers.frameOptions((opts) -> opts.disable()));

        return http.build();
    }

}
