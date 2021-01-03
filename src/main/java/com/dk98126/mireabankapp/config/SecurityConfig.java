package com.dk98126.mireabankapp.config;

import com.dk98126.mireabankapp.model.enm.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String BCRYPT_ID = "bcrypt";
    private static final String NOOP_ID = "noop";
    private static final Map<String, PasswordEncoder> encoders = new HashMap<>();

    static {
        encoders.put(BCRYPT_ID, new BCryptPasswordEncoder());
        encoders.put(NOOP_ID, NoOpPasswordEncoder.getInstance());
    }

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig() {
        this.passwordEncoder = new DelegatingPasswordEncoder(BCRYPT_ID, encoders);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Bean
    // TODO разобраться, для чего он
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO чуть-чуть доконфигурировать сессию
        http
                .authorizeRequests()
                .antMatchers("/main", "/registration", "/login", "/images/**", "/favicon.ico", "/webjars/**").permitAll()
                .antMatchers("/users-statuses","/managing-requests").hasAuthority("MANAGER")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/my-room")
                .permitAll()

                .and()

                .logout()
                .addLogoutHandler(((request, response, authentication) -> {
                    for (Cookie cookie : request.getCookies()) {
                        String cookieName = cookie.getName();
                        Cookie cookieToDelete = new Cookie(cookieName, null);
                        cookieToDelete.setMaxAge(0);
                        response.addCookie(cookieToDelete);
                    }
                }))

                .and()

                .sessionManagement()
                .sessionFixation()
                .migrateSession()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/main")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/main");
    }
}
