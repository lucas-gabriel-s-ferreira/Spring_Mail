package com.ms.springmail.registration;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Registration {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

}
