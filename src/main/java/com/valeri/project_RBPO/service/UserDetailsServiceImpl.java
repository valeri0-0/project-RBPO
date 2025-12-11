package com.valeri.project_RBPO.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valeri.project_RBPO.entity.AppUser;
import com.valeri.project_RBPO.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService
{

    // Загрузки данных пользователя
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException
    {
        AppUser user = userRepository.findByEmail(emailOrUsername)
                .orElseGet(() -> userRepository.findByUsername(emailOrUsername)
                        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден!")));

        return User.builder()
                .username(user.getEmail() != null ? user.getEmail() : user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().getGrantedAuthorities())
                .build();
    }
}