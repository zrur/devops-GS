package br.com.fiap.aquamind.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * Classe utilitária para criar, parsear e validar tokens JWT.
 * Compatível com JJWT 0.11.x (usa parserBuilder() e Keys.hmacShaKeyFor(...)).
 */
@Component
public class JwtUtil {

    /**
     * Chave secreta para assinar o JWT. Defina em application.properties:
     *   app.jwt.secret=UmaChaveSuperSecretaEComplexa123!
     */
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    /**
     * Tempo de expiração do token em milissegundos. Defina em application.properties:
     *   app.jwt.expiration-ms=86400000
     */
    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    /**
     * Chave HMAC efetiva gerada a partir de jwtSecret.
     * Inicializada em @PostConstruct para converter a string em um objeto java.security.Key.
     */
    private Key key;

    /**
     * Após injetar jwtSecret, converte-o em um Key HMAC de 256 bits.
     */
    @PostConstruct
    public void init() {
        // Gera uma "Key" HMAC-SHA a partir do texto jwtSecret
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gera um token JWT válido por jwtExpirationMs milissegundos,
     * com "subject" igual ao userDetails.getUsername().
     */
    public String gerarToken(UserDetails userDetails) {
        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())  // normalmente, o e-mail do usuário
                .setIssuedAt(agora)
                .setExpiration(dataExpiracao)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrai o username (e-mail) do token JWT.
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    /**
     * Verifica se o token é válido (username bate e não expirou).
     */
    public boolean validarToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpirado(token));
    }

    /**
     * Verifica se o token já expirou.
     */
    private boolean isTokenExpirado(String token) {
        Date dataExp = parseClaims(token).getExpiration();
        return dataExp.before(new Date());
    }

    /**
     * Faz o parsing das Claims a partir do token JWT usando a chave HMAC.
     */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)               // usa o Key gerado em init()
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
