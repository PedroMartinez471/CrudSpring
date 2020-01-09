package com.crud.controllers;

import com.crud.models.User;
import com.crud.models.UserCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@SpringBootApplication
public class ControllerCrud {
	

    @Autowired
    private UserCrud uc;

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
        if (bindingResult.hasErrors())  return "register";
        uc.save(user);
        mp.put("user", user);
        return "user";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/login")
    public String loginUser(@Valid User user, BindingResult bindingResult, ModelMap mp) {
        if(bindingResult.hasErrors()) {
            mp.put("errors", "Invalid username or password!");
            return "login";
        }
        User userEntity = uc.findUserByName(user.getName());
        if (!userEntity.getPassword().equals(user.getPassword())) return "login";
        mp.put("user", userEntity);
        return "user";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable("id") long id, ModelMap mp) {
        User user = uc.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        mp.put("user", user);
        return "edit";
    }
	@PostMapping("/user/edit")
    public String edit(@Valid User user, BindingResult bindingResult, ModelMap mp){
		if (bindingResult.hasErrors()) {
            mp.put("users", uc.findAll());
            return "users";
        }
        User userEntity = uc.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + user.getId()));
        userEntity.setName(user.getName());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        uc.save(userEntity);
        mp.put("user", userEntity);
        return "user";
    }

	@GetMapping("/user/delete/{id}")
    public String deleteById(@PathVariable("id") Long id, ModelMap mp){
		User user  = uc.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
		uc.delete(user);
        mp.put("users", uc.findAll());
        return "users";
    }

}
