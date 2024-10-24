package com.data.fi.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.data.fi.model.MagicLink;
import com.data.fi.repository.MagicLinkRepository;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TokenService tokenService; // Injetando o TokenService

    @Autowired
    private MagicLinkRepository magicLinkRepository; // Repositório para armazenar tokens
    
    @Autowired
    @Lazy
    private UserService userService; // Injetando o UserService

    public String sendRecoveryEmail(String email) {
        // Gere um token de recuperação real
        String recoveryToken = tokenService.generateToken(); // Gere um token seguro
        String recoveryLink = "https://yourapp.com/recover?token=" + recoveryToken;

        // Salvar o token e associá-lo ao usuário
        MagicLink magicLink = new MagicLink();
        magicLink.setToken(recoveryToken);
        magicLink.setUserId(findUserIdByEmail(email)); // Busca o ID do usuário pelo e-mail
        magicLink.setCreatedAt(LocalDateTime.now());
        magicLink.setExpiration(LocalDateTime.now().plusMinutes(15)); // Define a expiração do token (15 minutos)

        magicLinkRepository.save(magicLink); // Salvar o link mágico no banco

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Recovery");
        message.setText("Click the link to recover your password: " + recoveryLink);
        
        emailSender.send(message);
        
        return recoveryLink;
    }

    public String sendMagicLink(String email) {
        String token = tokenService.generateToken(); // Gere um token único
        String magicLink = "https://yourapp.com/magic-login?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Magic Login Link");
        message.setText("Click the link to log in: " + magicLink);
        
        emailSender.send(message);
        
        return magicLink;
    }

    private Long findUserIdByEmail(String email) {
        // Chama o UserService para buscar o usuário pelo e-mail
        return userService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found")).getId();
    }
}
