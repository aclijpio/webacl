package ru.aclij.webacl.security.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtTokenProperties {
    private String secret;
    private Duration lifetime;
}
