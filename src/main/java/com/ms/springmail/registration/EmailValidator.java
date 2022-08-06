package com.ms.springmail.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String email) {
        if(email.contains("@")) {
            return true;
        }else{
            return false;
        }
    }
}
