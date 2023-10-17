package ru.aclij.webacl.security.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
