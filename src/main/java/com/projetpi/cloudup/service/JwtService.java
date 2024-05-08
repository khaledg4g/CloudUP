package com.projetpi.cloudup.service;
import com.projetpi.cloudup.entities.Token;
import com.projetpi.cloudup.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Object;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    @Autowired
    private TokenRepository tokenRepository;

    @Value("${application.security.expiration}")

    private long jwtExpiration;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSingInKey())
                .build().parseClaimsJws(token)
                .getBody();

    }


    public String generateToken (UserDetails userDetails){
        return generateToken2(new HashMap<>(), userDetails);
    }

    public String generateToken2(Map<String,Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraclaims,
            UserDetails userDetails,
            long jwtExpiration) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts
                .builder()
                .setClaims(extraclaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("authorities", authorities)
                .signWith(getSingInKey())
                .compact()
                ;
    }

    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private Key getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public long getUserIdByToken(String token) {
        log.info("Token received: " + token);

        try {
            // Décode le token JWT pour extraire les claims
            Jws<Claims> claims = (Jws<Claims>) Jwts.parserBuilder()
                    .setSigningKey(getSingInKey())
                    .build()
                    .parseClaimsJws(token);


            // Récupère le nom d'utilisateur à partir des claims du token
            String username = claims.getBody().getSubject();

            // Parcoure la liste des tokens pour trouver l'utilisateur correspondant
            List<Token> tokenList = tokenRepository.findAll();
            for (Token t : tokenList) {
                if (t.getUser().getUsername().equals(username)) {
                    return t.getUser().getIdUser();
                }
            }
        } catch (Exception e) {
            log.error("Error decoding token or retrieving user ID: " + e.getMessage());

        }
        return 0; // Retourne 0 si aucun utilisateur correspondant n'est trouvé ou s'il y a une erreur
    }

}
