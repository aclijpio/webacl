package ru.aclij.webacl.apps.chess.supp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "unique-id")
@Data
public class UniqueIdProperties {
    private int length;
    private String characters;
}