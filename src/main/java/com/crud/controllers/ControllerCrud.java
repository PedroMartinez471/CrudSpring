package com.crud.controllers;

import com.crud.models.User;
import com.crud.models.UserPrincipal;
import com.crud.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@SpringBootApplication
public class ControllerCrud {
	

    @Autowired
    private UserRepository uc;

    @GetMapping({ "/", "/users" })
    public String usersList(ModelMap mp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            mp.put("auth_username", auth.getName());
        }
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

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, ModelMap mp) {
        if (error != null) mp.put("error", "Invalid username and password!");
        if (logout != null) mp.put("message", "You've been logged out successfully.");
        return "login";
    }

    @GetMapping("/user/me")
    public String profileUser(ModelMap mp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        mp.put("user", ((UserPrincipal) auth.getPrincipal()).getUser());
        return "user";
    }
    @GetMapping("/user/{username}")
    public String profileUser(@PathVariable(name = "username") String username, ModelMap mp) {
        mp.put("user", uc.findByUsername(username));
        return "user";
    }

    @GetMapping("/user/edit")
    public String editUser(ModelMap mp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = uc.findByUsername(((UserPrincipal) auth.getPrincipal()).getUsername());
        mp.put("user", user);
        mp.put("id", user.getId());
        return "edit";
    }
	@PostMapping("/user/edit/{id}")
    public String edit(@PathVariable("id") Long id, @Valid User user, BindingResult bindingResult, ModelMap mp) {
		if (bindingResult.hasErrors()) return "redirect:/";
        User userEntity = uc.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassWithoutEncrypt(user.getPassword());
        uc.save(userEntity);
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        ((UserPrincipal) authUser.getPrincipal()).setUser(userEntity);
        return "redirect:/";
    }
	@GetMapping("/user/delete/me")
    public String deleteById(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authUser = ((UserPrincipal) auth.getPrincipal()).getUser();
        Long id = authUser.getId();
		User userEntity  = uc.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
		uc.delete(userEntity);
        return "redirect:/logout";
    }

}
