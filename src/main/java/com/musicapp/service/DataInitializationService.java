package com.musicapp.service;

import com.musicapp.document.User;
import com.musicapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataInitializationService implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        createDefaultAdminUser();
    }

    private void createDefaultAdminUser() {
        //check if the admin already exists or not
        if (!userRepository.existsByEmail("admin@music.com")) {
            User adminUser = User.builder()
                    .email("admin@music.com")
                    .password("admin123")
                    .role(User.Role.ADMIN)
                    .build();

            userRepository.save(adminUser);
            log.info("Default admin user created with email: admin@music.com password: admin124");
        } else {
            log.info("admin user already exists with email:");
        }
    }
}
