package com.dk98126.mireabankapp.service;

import com.dk98126.mireabankapp.model.BankUserPrincipal;
import com.dk98126.mireabankapp.model.entity.UserEntity;
import com.dk98126.mireabankapp.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BankUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    // TODO добавить обработку ошибок
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRepo.findByLogin(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new BankUserPrincipal(userEntity);
    }
}
