package ru.aclij.webacl.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.aclij.webacl.security.dtos.JwtRequest;
import ru.aclij.webacl.security.dtos.RegistrationUserDto;
import ru.aclij.webacl.security.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestParam String username, @RequestParam String password) {
        JwtRequest request = new JwtRequest();
        request.setUsername(username);
        request.setPassword(password);
        return authService.createAuthToken(request);
    }
/*    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }*/

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }
}