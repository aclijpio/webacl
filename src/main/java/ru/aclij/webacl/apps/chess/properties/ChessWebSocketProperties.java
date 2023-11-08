package ru.aclij.webacl.apps.chess.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "chess.websocket")
@Data
public class ChessWebSocketProperties {
    private String responseUrl;
}

