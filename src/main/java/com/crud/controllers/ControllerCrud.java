package com.crud.controllers;

import com.crud.models.User;
import com.crud.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@SpringBootApplication
public class ControllerCrud {
	

    @Autowired
    private UserRepository uc;

    @GetMapping({ "/", "/users" })
    public String usersList(ModelMap mp){
        mp.put("users", uc.findAll());
        return "users";
    }

    @GetMapping("/register")
    public String register(ModelMap mp) {
        mp.put("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult bindingResult, ModelMap mp) {
        if (bindingResult.hasErrors()) return "register";
        uc.save(user);
        mp.put("user", user);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profileUser() {
        return "profile";
    }
    @GetMapping("/profile/edit")
    public String editUser(@Valid User user, ModelMap mp) {
        Long id = user.getId();
        User userEntity = uc.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        mp.put("user", userEntity);
        return "edit";
    }
	@PostMapping("/profile/edit")
    public String edit(@Valid User user, BindingResult bindingResult, ModelMap mp){
		if (bindingResult.hasErrors()) {
            mp.put("users", uc.findAll());
            return "users";
        }
        Long id = user.getId();
        User userEntity = uc.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        uc.save(userEntity);
        mp.put("user", userEntity);
        return "user";
    }
	@GetMapping("/profile/delete")
    public String deleteById(@Valid User user, ModelMap mp){
        Long id = user.getId();
		User userEntity  = uc.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
		uc.delete(userEntity);
        mp.put("users", uc.findAll());
        return "users";
    }

}
