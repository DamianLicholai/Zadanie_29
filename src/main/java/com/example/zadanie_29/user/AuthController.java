package com.example.zadanie_29.user;

import com.example.zadanie_29.mail.MailSenderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private UserService userService;



    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/logowanie")
    public String loginForm(@RequestParam(required = false) String error, Model model) {
        boolean showErrorMessage = false;

        if (error != null) {
            showErrorMessage = true;
        }
        model.addAttribute("showErrorMessage", showErrorMessage);
        return "login";
    }

    @PostMapping("/rejestracja")
    public String register(User user, Model model){
        boolean showRegisterInformation = false;
        if (user != null) {
            showRegisterInformation = true;
        }
        model.addAttribute("showRegisterInformation", showRegisterInformation);

        String userName = user.getEmail();
        String rawPassword = user.getPassword();
        userService.registerUser(userName, rawPassword);
        return "login";
    }


    @GetMapping("/reset")
    public String resetForm() {
        return "resetForm";
    }

    @PostMapping("reset")
    public String resetPasswordLinkSent(@RequestParam String email) {
        userService.sendPasswordResetLink(email);
        return "resetFormSend";
    }

    @GetMapping("/resetHasla")
    public String resetPassword(@RequestParam("klucz") String key, Model model) {
            model.addAttribute("key", key);
        return "resetFormWithKey";
    }



    @PostMapping("/resetEnding")
    public String res(@RequestParam String key, @RequestParam String password) {
        userService.updateUserPassword(key, password);
        return "resetSucces";
    }

}
