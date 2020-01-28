package pl.filip.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.filip.shop.dto.EditUserDto;
import pl.filip.shop.dto.PasswordUserDto;
import pl.filip.shop.dto.UserDto;
import pl.filip.shop.mapper.UserMapper;
import pl.filip.shop.model.User;
import pl.filip.shop.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
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

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user",  new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@Valid @ModelAttribute("user") UserDto userDto,
                                      BindingResult result){
        User existingEmail = userService.findByEmail(userDto.getEmail());
        if (existingEmail != null){
            result.rejectValue("email", null, "Juz istnieje taki adres email");
        }
        if (result.hasErrors()){
            return "registration";
        }

        userService.save(userDto);
        return "redirect:/login";
    }

    @GetMapping("/information")
    public String userInformation(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "information";
    }

    @GetMapping("/edit_user")
    public String editProduct(Model model, Principal principal) {
        User object = userService.findByEmail(principal.getName());
        if (object != null) {
            model.addAttribute("user", userMapper.toEditUser(object));
            return "edit_user.html";
        }
        return "not_found.html";
    }

    @PostMapping("/edit_user")
    public String editedUser(@Valid @ModelAttribute("user") EditUserDto editUserDto,
                             BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "edit_user";
        }
        userService.editUser(userMapper.toUser(editUserDto), principal.getName());
        return "redirect:/information";
    }


    @GetMapping("/edit_password")
    public String editPassword(Model model) {
        model.addAttribute("user_password", new PasswordUserDto());
        return "editPassword.html";
    }

    @PostMapping("/edit_password")
    public String editPassword(@Valid @ModelAttribute("user_password") PasswordUserDto password,
                               BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return "editPassword";
        }
        userService.editPassword(principal.getName(), password);
        return "redirect:/logout";
    }

    @GetMapping("/enable")
    public String enableUser(Principal principal,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        userService.deleteAccount(principal.getName());
        return "redirect:/login";
    }
}
