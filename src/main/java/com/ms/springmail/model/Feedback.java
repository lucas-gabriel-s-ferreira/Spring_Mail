package com.ms.springmail.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.time.LocalDate;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Feedback {
    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    private String email;
    private String number;
    private String message;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    //nao tem sentido agendar uma data em um feedback porem eu nao tenho criatividade pra elaborar essas coisas

    public Feedback(String name, String email, String number, String message, LocalDate date) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.message = message;
        this.date = date;
    }
}
