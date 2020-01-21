package pl.filip.shop.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.filip.shop.model.User;
import pl.filip.shop.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/newRegistration")
    public String register(Model model) {
        model.addAttribute("registration_user", new User());
        return "registration";
    }

    @PostMapping("/newRegistration")
    public String register(User user) {
        userService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/information")
    public String userInformation(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "information";
    }

    @GetMapping("/edit_user")
    public String editProduct(Model model, Principal principal) {
        User object = userService.findByUsername(principal.getName());
        if (object != null) {
            model.addAttribute("user_details", object);
            return "edit_user.html";
        }
        return "not_found.html";
    }

    @PostMapping("/editedUser")
    public String editedUser(User user, Principal principal) {
        userService.editUser(user, principal.getName());
        return "redirect:/information";
    }

    @GetMapping("/enable")
    public String enableUser(Principal principal,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        User disable = userService.disableAcc(principal.getName());

        return "redirect:/login";
    }
}
