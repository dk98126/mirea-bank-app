package com.dk98126.mireabankapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ID_FOR_ENCODE = "bcrypt";
    private static final Map<String, PasswordEncoder> encoders = new HashMap<>();

    static {
        encoders.put(ID_FOR_ENCODE, new BCryptPasswordEncoder());
    }

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig() {
        this.passwordEncoder = new DelegatingPasswordEncoder(ID_FOR_ENCODE, encoders);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO нормально настроить безопасность
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/my-room")
                .permitAll();
    }
}
