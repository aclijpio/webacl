package ru.aclij.webacl;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

@SpringBootApplication
public class WebaclApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebaclApplication.class, args);
    }

}
