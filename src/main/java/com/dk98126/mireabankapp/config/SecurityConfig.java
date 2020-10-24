package com.dk98126.mireabankapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebApplicationInitializer {
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

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO нормально настроить безопасность

        http
                .sessionManagement()
                .sessionFixation()
                .migrateSession()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/main")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/main");
        http.
                logout()
                .deleteCookies("JSESSIONID");

        http
                .authorizeRequests()
                .antMatchers("/main", "/registration", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/my-room")
                .permitAll();
    }

    @Override
    public void onStartup(ServletContext servletContext) {
        servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
    }
}
