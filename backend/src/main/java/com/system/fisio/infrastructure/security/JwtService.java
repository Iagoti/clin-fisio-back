package com.system.fisio.infrastructure.security;

import com.system.fisio.application.ports.ITokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class JwtService implements ITokenService {

    /**
     * Versão do formato das claims do token. Incrementar sempre que o formato mudar
     * de forma incompatível (ex.: da claim única "role" para uma lista "permissoes").
     * Tokens emitidos com uma versão diferente são rejeitados explicitamente em
     * {@link #tokenValido}, forçando um "faça login novamente" claro em vez de um
     * 403/401 confuso por causa de uma claim que não existe mais.
     */
    public static final int TOKEN_VERSION = 2;

    private final SecretKey key;
    private final long expMinutes;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration-minutes}") long expMinutes
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expMinutes = expMinutes;
    }

    @Override
    public String gerarToken(Integer cdUsuario, String login, Set<String> permissoes, Set<String> roles) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expMinutes * 60);
        return Jwts.builder()
                .subject(login)
                .claims(Map.of(
                        "cdUsuario", cdUsuario,
                        "ver", TOKEN_VERSION,
                        "permissoes", List.copyOf(permissoes != null ? permissoes : Set.of()),
                        "roles", List.copyOf(roles != null ? roles : Set.of())
                ))
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    @Override
    public boolean tokenValido(String token) {
        try {
            var claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            Object ver = claims.get("ver");
            return ver instanceof Number number && number.intValue() == TOKEN_VERSION;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String extrairLogin(String token) {
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> extrairPermissoes(String token) {
        Object permissoes = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload().get("permissoes");
        return permissoes == null ? List.of() : (List<String>) permissoes;
    }
}
