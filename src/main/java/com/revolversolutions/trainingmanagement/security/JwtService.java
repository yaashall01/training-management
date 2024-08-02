package com.revolversolutions.trainingmanagement.security;

import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.access-token-expiration}")
    private long accessTokenExpire;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpire;

    private final TokenRepository tokenRepository;

    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    /**
     * Extract username from the token
     * @param token
     * @return String
     */
    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get("username", String.class));
    }

    /**
     * Check if the token is valid
     * @param token
     * @param user
     * @return boolean
     */

    public boolean isValid(String token, User user) {
        String username = extractUsername(token);
        String email = extractEmail(token);

        boolean validToken = tokenRepository
                .findByAccessToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (email.equals(user.getEmail())|| username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }


    /**
     * Check if the refresh token is valid
     * @param token
     * @param user
     * @return boolean
     */
    public boolean isValidRefreshToken(String token, User user) {
        String username = extractUsername(token);
        String email = extractEmail(token);

        boolean validRefreshToken = tokenRepository
                .findByRefreshToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (email.equals(user.getEmail()) || username.equals(user.getUsername())) && !isTokenExpired(token) && validRefreshToken;
    }

    /**
     * Check if the token is expired
     * @param token
     * @return boolean
     */

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract expiration date from the token
     * @param token
     * @return Date
     */
    private Date extractExpiration(String token ){
        return extractClaim(token, Claims::getExpiration);
    }


    /**
     * Extract claim from the token
     * @param token
     * @param resolver
     * @return T
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }


    /**
     * Extract all claims from the token
     * @param token
     * @return Claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateAccessToken(User user){
        return generateToken(user, accessTokenExpire);
    }

    public String generateRefreshToken(User user){
        return generateToken(user, refreshTokenExpire);
    }

    private String generateToken(User user, long expireTime){
        String token = Jwts
                .builder()
                .subject(user.getEmail())
                .claim("role", user.getUserRole())
                .claim("username", user.getUserName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey())
                .compact();
        return token;
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
