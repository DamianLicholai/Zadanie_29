package com.example.zadanie_29;

import com.example.zadanie_29.user.User;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("user", new User());
        return "home";
    }

    @GetMapping("/secure")
    public String secure() {
        return "secure";
    }






}
