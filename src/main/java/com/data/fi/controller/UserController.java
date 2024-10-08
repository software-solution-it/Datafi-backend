package com.data.fi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.fi.dto.CreateUserRequestDTO;
import com.data.fi.dto.CreateUserResposeDTO;
import com.data.fi.dto.PasswordRecoveryRequestDTO;
import com.data.fi.dto.PasswordRecoveryResponseDTO;
import com.data.fi.dto.UpdateUserRequestDTO;
import com.data.fi.dto.UserResponseDTO;
import com.data.fi.model.User;
import com.data.fi.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Criação de um novo usuário
    @PostMapping("/create")
    public ResponseEntity<CreateUserResposeDTO> createUser(@RequestBody CreateUserRequestDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setEmailConfirmed(false); // Novo usuário ainda não tem email confirmado

        User createdUser = userService.saveUser(user);
        CreateUserResposeDTO responseDTO = new CreateUserResposeDTO(
            createdUser.getEmail(),
            createdUser.getId()
        );
        return ResponseEntity.ok(responseDTO);
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            UserResponseDTO responseDTO = new UserResponseDTO(
                foundUser.getId(),
                foundUser.getEmail(),
                foundUser.getRole(),
                foundUser.isEmailConfirmed()
            );
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDTO userDetails) {
        User user = new User();
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());
        user.setEmailConfirmed(userDetails.isEmailConfirmed());

        User updatedUser = userService.updateUser(id, user);
        UserResponseDTO responseDTO = new UserResponseDTO(
            updatedUser.getId(),
            updatedUser.getEmail(),
            updatedUser.getRole(),
            updatedUser.isEmailConfirmed()
        );
        return ResponseEntity.ok(responseDTO);
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Recuperação de senha
    @PostMapping("/recover-password")
    public ResponseEntity<PasswordRecoveryResponseDTO> recoverPassword(@RequestBody PasswordRecoveryRequestDTO requestDTO) {
        String email = requestDTO.getEmail().trim();
        String recoveryLink = userService.sendRecoveryEmail(email);
        PasswordRecoveryResponseDTO responseDTO = new PasswordRecoveryResponseDTO("Recovery email sent to: " + recoveryLink);
        return ResponseEntity.ok(responseDTO);
    }

    // Confirmação de email via link mágico (token)
    @GetMapping("/confirm-recovery/{token}")
    public ResponseEntity<String> confirmEmail(@PathVariable String token) {
        boolean confirmed = userService.confirmRecoveryToken(token);
        if (confirmed) {
            return ResponseEntity.ok("Email confirmed successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired confirmation link.");
        }
    }
}
