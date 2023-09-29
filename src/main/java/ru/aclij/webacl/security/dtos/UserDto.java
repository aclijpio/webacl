package ru.aclij.webacl.security.dtos;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDto {
    private String user;
    private String password;
    private String email;
}
