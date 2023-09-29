package ru.aclij.webacl.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.aclij.webacl.controllers.dtos.AppError;
import ru.aclij.webacl.security.dtos.JwtRequest;
import ru.aclij.webacl.security.dtos.JwtResponse;
import ru.aclij.webacl.security.dtos.RegistrationUserDto;
import ru.aclij.webacl.security.dtos.UserDto;
import ru.aclij.webacl.security.entities.User;
import ru.aclij.webacl.security.utils.JwtTokenUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest request) {
        System.out.println(request.getName());
        System.out.println(request.getPassword());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getName(),
                    request.getPassword()
            ));
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect login or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(request.getName());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto){
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword()))
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Password mismatch."), HttpStatus.BAD_REQUEST);
        if (userService.findByName(registrationUserDto.getName()).isPresent())
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "User already exists."), HttpStatus.BAD_REQUEST);

        userService.createNewUser(registrationUserDto);

        return ResponseEntity.ok(registrationUserDto);
    }

}
