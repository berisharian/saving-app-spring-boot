package finki.ukim.mk.savingapp.web;

import finki.ukim.mk.savingapp.model.User;
import finki.ukim.mk.savingapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showUsers(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam(required = false) String email,
                            Model model) {

        List<User> users = email != null
                ? List.of(userService.findByEmail(email))
                : userService.listAll();

        model.addAttribute("users", users);
        model.addAttribute("bodyContent", "users/list");
        model.addAttribute("email", email);
        return "master-template";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {

        User user = userService.findById(id);

        model.addAttribute("user", user);
        model.addAttribute("bodyContent", "users/userDetails");

        return "master-template";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("bodyContent", "users/userForm");
        return "master-template";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam String name,
                          @RequestParam String surname,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String phone,
                          @RequestParam String birthDate) {

        LocalDate birthDateParsed = LocalDate.parse(birthDate);
        userService.createUser(name, surname, email, password, phone, birthDateParsed);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {

        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("bodyContent", "users/userForm");

        return "master-template";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String phone,
                           @RequestParam String birthDate) {

        LocalDate birthDateParsed = LocalDate.parse(birthDate);
        userService.updateUser(id, name, surname, email, password, phone, birthDateParsed);
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
