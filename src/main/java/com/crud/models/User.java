package com.crud.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
 
@Entity
public class User {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Length(min=5, max=24)
    private String name;
    @NotNull
    @Length(min=4, max=100)
    private String password;
    @NotNull
    @Email
    private String email;
 
    public User() {
        super();
    }
 
    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
 
    public long getId() {
        return id;
    }
 
    /*public void setId(long id) {
        this.id = id;
    }*/
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }

}