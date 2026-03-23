package com.it.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/")
    public String index() {
        log.info("access index page, redirect to home");
        return "redirect:/page/home";
    }

    @GetMapping("/login")
    public String loginPage() {
        log.info("access login page");
        return "login";
    }

    @GetMapping("/user/login")
    public String loginPage2() {
        log.info("access login page2");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        log.info("access register page");
        return "register";
    }

    @GetMapping("/user/register")
    public String registerPage2() {
        log.info("access register page2");
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.info("user logout");
        session.removeAttribute("userInfo");
        session.removeAttribute("userId");
        return "redirect:/login";
    }

    @GetMapping("/my_page")
    public String myPage(HttpSession session, Model model) {
        log.info("access my page");
        Object user = session.getAttribute("userInfo");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "my_page";
    }

    @GetMapping("/user/myPage")
    public String myPage2(HttpSession session, Model model) {
        log.info("access my page2");
        Object user = session.getAttribute("userInfo");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "my_page";
    }
}
