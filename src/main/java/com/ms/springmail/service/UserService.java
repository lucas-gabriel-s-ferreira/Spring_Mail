package com.ms.springmail.service;

import com.ms.springmail.model.User;
import com.ms.springmail.registration.token.ConfirmationToken;
import com.ms.springmail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public String signUpUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        isEmailAvailable(user.getEmail());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        // criação do token de confirmação
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // envia o token para o email do usuário

        return token;
    }

    public void isEmailAvailable(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new DataIntegrityViolationException("Email já em uso");
        });
    }

    public int enableUser(String email){
        return userRepository.enableUser(email);
    }
}
