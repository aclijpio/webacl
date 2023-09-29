package ru.aclij.webacl.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils  {
    private final String secret;
    private final Duration lifetime;

    @Autowired
    public JwtTokenUtils(JwtTokenProperties properties) {
        this.secret = properties.getSecret();
        this.lifetime = properties.getLifetime();
    }
    public String generateToken(UserDetails userDetails){
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        Date issued = new Date();
        Date expired = new Date(issued.getTime() + this.lifetime.toMillis());
        return JWT.create()
                .withClaim("roles", rolesList)
                .withSubject(userDetails.getUsername())
                .withIssuedAt(issued)
                .withExpiresAt(expired)
                .sign(Algorithm.HMAC256(secret));
    }
    public String getUserName(String token){
        return getAllClaimsFromToken(token)
                .getSubject();
    }
    public List<String> getRoles(String token){
        return getAllClaimsFromToken(token)
                .getClaim("roles")
                .asList(String.class);
    }
    public DecodedJWT getAllClaimsFromToken(String token){
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token);
    }
}
