package webapp.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  // Изменение здесь
import org.springframework.web.bind.annotation.*;
import webapp.spring.model.User;
import webapp.spring.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {
    //Spring (через ApplicationContext, который использует BeanFactory) видит @Controller.
    //Вызывает конструктор UserController().
    //Внедряет UserService через @Autowired.
    //Вызывает new UserController() и внедряет UserService через @Autowired для создания бина с помощью бин-фактори



    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/webapp";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/webapp";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/webapp";
    }
}