package ru.aclij.webacl.security.services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user.service")
@Data
public class RoleServiceProperties {
    private String defaultRole;
}
