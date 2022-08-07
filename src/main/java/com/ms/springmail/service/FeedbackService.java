package com.ms.springmail.service;

import com.ms.springmail.model.Feedback;
import com.ms.springmail.registration.EmailValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final EmailService emailService;
    private final EmailValidator emailValidator;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public String sendForm(Feedback request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalArgumentException("Invalid email");
        }
        emailService.sendForm(buildForm(request.getName(), request.getEmail(), request.getNumber(), request.getMessage(), request.getDate()));
        return "Email sent";
    }

    private String buildForm(String name, String email, String number, String message, LocalDate date) {
        return "<html>\n" +
                "<body>\n" +
                "<h1>Formulario de agendamento</h1>\n" +
                "<p>Nome: "+name+"</p>\n" +
                "<p>Email: "+email+"</p>\n" +
                "<p>Numero: "+number+"</p>\n" +
                "<p>Data marcada: "+date.format(dateFormatter)+"</p>\n" +
                "<p>Mensagem: "+message+"</p>\n" +
                "</body>\n" +
                "</html>";
    }
}
