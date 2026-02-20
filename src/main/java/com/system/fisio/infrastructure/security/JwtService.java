package com.system.fisio.infrastructure.security;

import com.system.fisio.application.ports.ITokenService;
import com.system.fisio.domain.enums.TipoUsuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService implements ITokenService {

    private final SecretKey key;
    private final long expMinutes;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration-minutes}") long expMinutes
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expMinutes = expMinutes;
    }

    public String gerarToken(Integer cdUsuario, String login, Integer tpUsuario) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expMinutes * 60);
        String role = null;
        try {
            TipoUsuario tipo = TipoUsuario.fromCodigo(tpUsuario);
            role = "ROLE_" + tipo.name();
        } catch (Exception e) {
        }

        if (role == null) {
            return Jwts.builder()
                    .subject(login)
                    .claims(Map.of("cdUsuario", cdUsuario, "tpUsuario", tpUsuario))
                    .issuedAt(Date.from(now))
                    .expiration(Date.from(exp))
                    .signWith(key)
                    .compact();
        }

        return Jwts.builder()
                .subject(login)
                .claims(Map.of("cdUsuario", cdUsuario, "tpUsuario", tpUsuario, "role", role))
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public boolean tokenValido(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extrairLogin(String token) {
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public String extrairRole(String token) {
        Object role = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload().get("role");
        return role == null ? null : role.toString();
    }
}