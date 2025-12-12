package com.valeri.project_RBPO.service;

import com.valeri.project_RBPO.entity.AppUser;
import com.valeri.project_RBPO.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService
{
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException
    {
        // 2 варианта поиска: сначала по email, потом по username
        AppUser user = userRepository.findByEmail(emailOrUsername)
                .orElseGet(() -> userRepository.findByUsername(emailOrUsername)
                        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + emailOrUsername)));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().getGrantedAuthorities())
                .build();
    }
}