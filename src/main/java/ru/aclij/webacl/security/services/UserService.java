package ru.aclij.webacl.security.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aclij.webacl.security.PasswordEncodeConfiguration;
import ru.aclij.webacl.security.dtos.RegistrationUserDto;
import ru.aclij.webacl.security.entities.Role;
import ru.aclij.webacl.security.entities.User;
import ru.aclij.webacl.security.repositories.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByName(String name){
        return userRepository.findByName(name);
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with name: '%s'.", username)));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(Role::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet())
        );
    }
    public void createNewUser(RegistrationUserDto registrationUserDto){
        userRepository.save(
                new User (
                        registrationUserDto.getName(),
                        passwordEncoder.encode(registrationUserDto.getPassword()),
                        registrationUserDto.getEmail(),
                        Set.of(roleService.getDefaultRole())
                )
        );
    }
}
