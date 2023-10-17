package ru.aclij.webacl.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aclij.webacl.security.entities.Role;
import ru.aclij.webacl.security.exceptions.DefaultRoleNotFoundException;
import ru.aclij.webacl.security.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleServiceProperties properties;

    public Role getDefaultRole() {
        System.out.println(properties.getDefaultRole());
        System.out.println(properties.getDefaultRole().length());
        return roleRepository.findByName(properties.getDefaultRole())
                .orElseThrow(() -> new DefaultRoleNotFoundException("Default role not found. user.service:default_role"));
    }
}
