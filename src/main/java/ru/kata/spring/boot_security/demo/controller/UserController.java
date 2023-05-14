package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/admin")
    public String getUsers(Model model, ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("user", userService.findByName(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", userService.getRoles());
        return "admin";
    }

    @GetMapping(value = "admin/add")
    public String addNewUser(Model model, ModelMap modelMap, Principal principal) {
//        model.addAttribute("user", new User());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", userService.getRoles());
        modelMap.addAttribute("user", userService.findByName(principal.getName()));
        return "addUser";
    }

    @RequestMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", userService.getRoles());
        model.addAttribute("users", userService.getAllUsers());
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "admin/updateUser/{id}")
    public String getUser(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("roles", userService.getRoles());
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping(value = "/updateUser")
    public String updateUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", userService.getRoles());
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/delete")
    public String deleteUserById(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String getUser(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("user", userService.findByName(principal.getName()));
        return "user";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }
}
