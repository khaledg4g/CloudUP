package com.projetpi.cloudup.service;

import com.projetpi.cloudup.RestController.AuthentificationRequest;
import com.projetpi.cloudup.RestController.AuthentificationResponse;
import com.projetpi.cloudup.RestController.RegistrationRequest;
import com.projetpi.cloudup.email.EmailServer;
import com.projetpi.cloudup.email.EmailTemplateName;
import com.projetpi.cloudup.entities.*;
import com.projetpi.cloudup.repository.TokeAuthRepository;
import com.projetpi.cloudup.repository.TokenRepository;
import com.projetpi.cloudup.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthentificationService{


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final TokeAuthRepository tokeAuthRepository;
    private final EmailServer emailServer;
    private final JwtService jwtService;
    private final AuthenticationManager authentificationManager;
    @Value("${application.security.mailing.frontend.activation-url}")
    private String activationUrl;



    public List<User> finAll(User user){
        var users = userRepository.findAll();
        return users;
    }
    public void register(RegistrationRequest request) throws MessagingException {
        if(!userRepository.existsByEmail(request.getEmail())) {
            var user = User.builder()
                    .nom(request.getNom())
                    .prenom(request.getPrenom())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                    .enabled(false)
                    .accountLocked(false)
                    .roles(request.getRoles())
                    .build();
            userRepository.save(user);
            sendValidationEmail(user);
        }


    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailServer.sendMail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATIE_ACCOUNT,
                activationUrl,
                newToken,
                "account Activation"
        );

    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(20))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;

    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }
    public AuthentificationResponse authenticate(AuthentificationRequest request) {
        var auth = authentificationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getMotDePasse()
                )
        );



        var userSaved = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow();

        var claims = new HashMap<String, Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken2(claims , user);
        revokeAllUserTokens(userSaved);
        saveUserToken(userSaved,jwtToken );
        return AuthentificationResponse
                .builder()
                .token(jwtToken)
                .build();
    }


    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokeAuthRepository.findAllValidTokensByUser(user.getIdUser());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokeAuthRepository.saveAll(validUserTokens);
    }
    private void saveUserToken(User user, String jwtToken)
    {
        var token = TokenAuth.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokeAuthRepository.save(token);
    }


    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findTokenByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiredAt())){
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired, A new token has been send to the same email ");
        }

        var user = userRepository.findById(savedToken.getUser().getIdUser()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.save(savedToken);

    }
}
