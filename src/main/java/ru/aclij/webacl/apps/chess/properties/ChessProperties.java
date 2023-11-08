package ru.aclij.webacl.apps.chess.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "chess.controller")
@Data
public class ChessProperties {
    private String end_point;
}
