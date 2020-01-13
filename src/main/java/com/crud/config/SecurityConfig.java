package com.crud.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                .jdbcAuthentication()
                .usersByUsernameQuery("select name, password, enabled from user where name=?");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(
    "/",
                "/js/**",
                "/css/**",
                "/img/**",
                "/webjars/**").permitAll()
            .antMatchers("/user/**").authenticated()
            .antMatchers("/", "/users").permitAll()//.authenticated()
            .   and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/profile")
                .and()
            .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
            .csrf().disable()
            .httpBasic();
    }

}