package com.scannercp.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler
        implements AuthenticationSuccessHandler {

    /*
     * =========================================================
     * LOGIN CORRECTO
     * =========================================================
     */

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        /*
         * -----------------------------------------------------
         * DETECTAR TIPO DE DISPOSITIVO
         * -----------------------------------------------------
         */

        boolean dispositivoMovil = esDispositivoMovil(request);

        /*
         * -----------------------------------------------------
         * VERIFICAR SI EL USUARIO ES ADMIN
         * -----------------------------------------------------
         */

        boolean esAdmin = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(
                        rol -> rol.equals("ROLE_ADMIN"));

        /*
         * -----------------------------------------------------
         * REDIRECCIÓN
         * -----------------------------------------------------
         */

        if (dispositivoMovil) {

            /*
             * ADMIN
             * SUPERVISOR
             * TECNICO
             *
             * Desde celular:
             * todos van al scanner.
             */

            response.sendRedirect(
                    request.getContextPath()
                            + "/scanner");

            return;
        }

        /*
         * -----------------------------------------------------
         * ACCESO DESDE PC
         * -----------------------------------------------------
         */

        if (esAdmin) {

            /*
             * ADMIN desde PC:
             * Dashboard.
             */

            response.sendRedirect(
                    request.getContextPath()
                            + "/");

            return;
        }

        /*
         * SUPERVISOR o TECNICO desde PC:
         * no tienen acceso al Dashboard.
         */

        response.sendRedirect(
                request.getContextPath()
                        + "/acceso-movil");
    }

    /*
     * =========================================================
     * DETECTAR DISPOSITIVO MÓVIL
     * =========================================================
     */

    private boolean esDispositivoMovil(
            HttpServletRequest request) {

        String userAgent = request.getHeader(
                "User-Agent");

        if (userAgent == null) {
            return false;
        }

        String navegador = userAgent.toLowerCase();

        return navegador.contains("android")
                || navegador.contains("iphone")
                || navegador.contains("ipad")
                || navegador.contains("ipod")
                || navegador.contains("mobile")
                || navegador.contains("windows phone");
    }
}