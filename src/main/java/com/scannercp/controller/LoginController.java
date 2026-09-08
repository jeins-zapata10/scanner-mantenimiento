package com.scannercp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {


    /*
     * =========================================================
     * MOSTRAR PANTALLA DE LOGIN
     * =========================================================
     */

    @GetMapping("/login")
    public String login() {

        return "login";
    }
}