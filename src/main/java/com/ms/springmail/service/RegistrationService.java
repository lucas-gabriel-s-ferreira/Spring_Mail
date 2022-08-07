package com.ms.springmail.service;

import com.ms.springmail.registration.Registration;
import com.ms.springmail.model.User;
import com.ms.springmail.model.enumeration.UserRole;
import com.ms.springmail.registration.EmailValidator;
import com.ms.springmail.registration.token.ConfirmationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final EmailValidator emailValidator;

    public String register(Registration request) {
      boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalArgumentException("Invalid email");
        }
        String token = userService.signUpUser(new User(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), UserRole.USER));
        String link = "http://localhost:8080/registration/confirm?token=" + token;
        emailService.send(request.getEmail(), buildEmail(request.getFirstName(), link));

        return token;
    }

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if(confirmationToken.getConfirmedDate() != null){
            throw new IllegalArgumentException("Email already confirmed");
        }

        LocalDateTime expirationDate = confirmationToken.getExpirationDate();

        if(expirationDate.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Token expired");
        }

        confirmationToken.setConfirmedDate(LocalDateTime.now());

        userService.enableUser(confirmationToken.getUser().getEmail());

        return "Email confirmed";
    }

    private String buildEmail(String name, String link) {
        return "<html>\n" +
                "<body>\n" +
                "<h1>Olá " + name + "</h1>\n" +
                "<p>Confirme seu email clicando no link abaixo</p>\n" +
                "<a href=\"" + link + "\">Clique aqui para confirmar seu email</a>\n" +
                "<p>O link expira em 15 minutos</p>\n" +
                "</body>\n" +
                "</html>";
    }

}
