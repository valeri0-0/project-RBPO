package com.valeri.project_RBPO.controller;

import com.valeri.project_RBPO.entity.AppUser;
import com.valeri.project_RBPO.model.RegisterDto;
import com.valeri.project_RBPO.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController
{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto)
    {
        // Проверка существования пользователя
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent())
        {
            return ResponseEntity.badRequest().body("Имя пользователя уже занято!");
        }

        if (userRepository.findByEmail(registerDto.getEmail()).isPresent())
        {
            return ResponseEntity.badRequest().body("Email уже используется!");
        }

        // Попытка создать ADMIN = запрет
        if (registerDto.getRole() != null && registerDto.getRole().equalsIgnoreCase("ADMIN"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Регистрация с ролью ADMIN запрещена!");
        }

        // Создание нового пользователя
        AppUser user = AppUser.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(com.valeri.project_RBPO.model.enums.ApplicationUserRole.USER)
                .build();

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Пользователь успешно зарегистрирован!");
    }
}