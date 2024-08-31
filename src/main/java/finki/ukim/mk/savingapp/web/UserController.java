package finki.ukim.mk.savingapp.web;

import finki.ukim.mk.savingapp.model.User;
import finki.ukim.mk.savingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getUserProfile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("bodyContent", "users/list");
        return "master-template";
    }

    @GetMapping("/edit")
    public String editUserProfile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("bodyContent", "users/userForm");
        return "master-template";
    }

    @PostMapping("/edit")
    public String updateUserProfile(@AuthenticationPrincipal User user,@ModelAttribute User updatedUser, Model model) {
        userService.updateUser(user.getUsername(), updatedUser);
        return "redirect:/users";
    }
}