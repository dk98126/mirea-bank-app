package com.dk98126.mireabankapp.model;

import com.dk98126.mireabankapp.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

// TODO разобраться и переконфигурить

@AllArgsConstructor
public class BankUserPrincipal implements UserDetails, Serializable {
    private static final long serialVersionUID = 1;

    private UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(userEntity.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userEntity.isBlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setLogin(String login) {
        userEntity.setLogin(login);
    }

    public void setMail(String mail) {
        userEntity.setMail(mail);
    }

    public void setPhoneNumber(String phoneNumber) {
        userEntity.setPhoneNumber(phoneNumber);
    }

    public void setFullName(String firstName, String middleName, String lastName) {
        userEntity.setFirstName(firstName);
        userEntity.setMiddleName(middleName);
        userEntity.setLastName(lastName);
    }
}
