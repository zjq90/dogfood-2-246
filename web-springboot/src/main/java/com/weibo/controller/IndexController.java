package com.weibo.controller;

import com.weibo.model.User;
import com.weibo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/user/login";
    }

    @GetMapping("/my_page")
    public String myPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }
        User user = userService.getUserByUserId(userId);
        model.addAttribute("user", user);
        return "my_page";
    }

    @GetMapping("/alter")
    public String alterPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }
        User user = userService.getUserByUserId(userId);
        model.addAttribute("user", user);
        return "alter";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
