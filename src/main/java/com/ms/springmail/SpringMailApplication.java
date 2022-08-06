package com.ms.springmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMailApplication.class, args);
        System.out.print("Spring Mail Application rodando corretamente");
    }

}
