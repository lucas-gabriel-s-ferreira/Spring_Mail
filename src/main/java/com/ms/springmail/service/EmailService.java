package com.ms.springmail.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void send(String to, String email){
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, "UTF-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("emailmailsendertest42@gmail.com");
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new IllegalStateException("Email sending failed");
        }
    }

    public void sendForm(String email){
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, "UTF-8");
            helper.setText(email, true);
            String ownerEmail = "gabrielslv718@gmail.com";
            helper.setTo(ownerEmail);
            helper.setSubject("Form");
            helper.setFrom("emailmailsendertest42@gmail.com");
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new IllegalStateException("Email sending failed");
        }
    }
}
