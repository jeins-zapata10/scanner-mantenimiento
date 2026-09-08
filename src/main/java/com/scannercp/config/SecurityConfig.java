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

                                                /*
                                                 * =====================================================
                                                 * ZONA ADMINISTRATIVA
                                                 * =====================================================
                                                 *
                                                 * Únicamente ADMIN puede administrar:
                                                 * - Dashboard
                                                 * - Usuarios
                                                 * - Máquinas
                                                 * - Piezas
                                                 */

                                                .requestMatchers(
                                                                "/",
                                                                "/dashboard/**",
                                                                "/usuarios/**",
                                                                "/maquinas/**",
                                                                "/piezas/**")
                                                .hasRole("ADMIN")

                                                /*
                                                 * =====================================================
                                                 * ZONA OPERATIVA
                                                 * =====================================================
                                                 *
                                                 * Los tres roles pueden utilizar
                                                 * el scanner desde el móvil.
                                                 */

                                                .requestMatchers(
                                                                "/scanner/**",
                                                                "/api/piezas/qr/**",
                                                                "/acceso-movil")
                                                .hasAnyRole(
                                                                "ADMIN",
                                                                "SUPERVISOR",
                                                                "TECNICO")

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
