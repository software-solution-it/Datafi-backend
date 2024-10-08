package com.data.fi.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.data.fi.config.BadRequestException;
import com.data.fi.model.MagicLink;
import com.data.fi.model.User;
import com.data.fi.repository.MagicLinkRepository;
import com.data.fi.repository.UserRepository;
import com.data.fi.util.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MagicLinkRepository magicLinkRepository;

    @Autowired
    JwtUtil jwtUtil;
    
    @Autowired
    @Lazy
    private EmailService emailService;

    // Expressão regular para validar o formato do e-mail
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public User saveUser(User user) {
        // Valida se o e-mail está em formato correto
        if (!isValidEmail(user.getEmail())) {
            throw new BadRequestException("Formato de e-mail inválido.");
        }

        // Verifica se o e-mail já está cadastrado
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BadRequestException("O usuário já existe.");
        }

        // Verifica se a senha é válida
        if (!isValidPassword(user.getPassword())) {
            throw new BadRequestException("A senha deve ter no mínimo 6 caracteres.");
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o usuário.", e);
        }
    }

    public Optional<User> findById(Long id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar o usuário.", e);
        }
    }

    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuário não encontrado."));

        // Valida se o e-mail está em formato correto
        if (!isValidEmail(userDetails.getEmail())) {
            throw new BadRequestException("Formato de e-mail inválido.");
        }

        try {
            existingUser.setEmail(userDetails.getEmail());
            
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                // Verifica se a senha é válida
                if (!isValidPassword(userDetails.getPassword())) {
                    throw new BadRequestException("A senha deve ter no mínimo 6 caracteres.");
                }
                existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }

            existingUser.setRole(userDetails.getRole());
            return userRepository.save(existingUser);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o usuário.", e);
        }
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException("Usuário não encontrado.");
        }

        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o usuário.", e);
        }
    }

    public String sendRecoveryEmail(String email) {
        try {
            return emailService.sendRecoveryEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail de recuperação.", e);
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar o usuário pelo e-mail.", e);
        }
    }

    public boolean confirmRecoveryToken(String token) {
        MagicLink magicLink = magicLinkRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Token inválido ou expirado."));

        if (magicLink.getExpiration().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token expirado.");
        }

        try {
            magicLinkRepository.delete(magicLink);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao confirmar o token de recuperação.", e);
        }
    }

    // Método que valida se o e-mail tem um formato correto
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    // Método que valida se a senha é forte o suficiente
    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}
