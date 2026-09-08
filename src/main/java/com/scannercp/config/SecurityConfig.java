package com.scannercp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import com.scannercp.security.LoginSuccessHandler;

@Configuration
public class SecurityConfig {

        /*
         * =========================================================
         * CODIFICADOR DE CONTRASEÑAS
         * =========================================================
         */

        @Bean
        public PasswordEncoder passwordEncoder() {

                return new BCryptPasswordEncoder();
        }

        /*
         * =========================================================
         * CONFIGURACIÓN DE SEGURIDAD
         * =========================================================
         */

        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http,
                        LoginSuccessHandler loginSuccessHandler)
                        throws Exception {

                http

                                /*
                                 * =====================================================
                                 * CSRF
                                 * =====================================================
                                 */

                                .csrf(csrf -> csrf.disable())

                                /*
                                 * =====================================================
                                 * AUTORIZACIÓN
                                 * =====================================================
                                 */

                                .authorizeHttpRequests(auth -> auth

                                                /*
                                                 * Recursos públicos
                                                 */

                                                .requestMatchers(
                                                                "/login",
                                                                "/css/**",
                                                                "/js/**",
                                                                "/images/**",
                                                                "/favicon.ico")
                                                .permitAll()

                                                /*
                                                 * Dashboard únicamente ADMIN
                                                 */

                                                .requestMatchers(
                                                                "/",
                                                                "/dashboard/**")
                                                .hasRole("ADMIN")

                                                /*
                                                 * Todo lo demás requiere sesión
                                                 */

                                                .anyRequest()
                                                .authenticated())

                                /*
                                 * =====================================================
                                 * LOGIN
                                 * =====================================================
                                 */

                                .formLogin(form -> form

                                                .loginPage("/login")

                                                .loginProcessingUrl("/login")

                                                .failureUrl("/login?error")

                                                /*
                                                 * Aquí entra nuestra lógica:
                                                 *
                                                 * móvil -> scanner
                                                 * PC ADMIN -> dashboard
                                                 * PC otros -> acceso-movil
                                                 */

                                                .successHandler(
                                                                loginSuccessHandler)

                                                .permitAll())

                                /*
                                 * =====================================================
                                 * LOGOUT
                                 * =====================================================
                                 */

                                .logout(logout -> logout

                                                .logoutUrl("/logout")

                                                .logoutSuccessUrl(
                                                                "/login?logout")

                                                .permitAll());

                return http.build();
        }

}
