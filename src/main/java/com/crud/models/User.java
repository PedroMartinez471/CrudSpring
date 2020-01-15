package com.crud.models;

import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@JsonSerializableSchema
@Entity
@Table(name = "User")
public class User {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Length(min=5, max=24)
    @Column(name="name", unique = true)
    private String username;
    @NotNull
    @Length(min=4, max=100)
    private String password;
    @NotNull
    @Email
    private String email;

    public long getId() {
        return id;
    }
 
    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = (new BCryptPasswordEncoder(4)).encode(password);
    }

    public void setPassWithoutEncrypt(String password) { this.password = password; }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }

}