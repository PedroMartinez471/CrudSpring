package com.crud.controllers;
//package com.crud;

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
    
	@GetMapping("/")
    public String UsersList(ModelMap mp){
        mp.put("users", uc.findAll());
    return "crud/list";
    }
	
	@GetMapping("/new")
    public String newUser(ModelMap mp){
		mp.put("user", new User());
        return "crud/new";
    }
	
	@PostMapping("/new")
	public String create(@Valid User user,
    	BindingResult bindingResult, ModelMap mp){
		if(bindingResult.hasErrors()){
	        return "crud/new";
	    }else{
	        uc.save(user);
	        mp.put("users", uc.findAll());
	        return "crud/list";
	    }
    }
	
	@GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, ModelMap mp){
        mp.put("user", uc.findById(id));
        return "crud/edit";
    }
     
	@PostMapping("/update")
    public String edit(@Valid User user, BindingResult bindingResult, ModelMap mp){
		if (bindingResult.hasErrors()) {
            mp.put("users", uc.findAll());
            return "crud/list";
        }
        User userEntity = uc.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + user.getId()));
        userEntity.setName(user.getName());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        uc.save(userEntity);
        mp.put("users", uc.findAll());
        return "crud/list";
    }
	
	
	@GetMapping("/delete/all")
    public String delete(ModelMap mp){
        uc.deleteAll();
        mp.put("users", uc.findAll());
        return "crud/list";
    }

	@GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id, ModelMap mp){
		User user  = uc.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
		uc.delete(user);
        mp.put("users", uc.findAll());
        return "crud/list";
    }

}
