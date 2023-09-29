package ru.aclij.webacl.security.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String name;
    private String password;
}
