package com.data.fi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.data.fi.dto.AuthenticationRequestDTO;
import com.data.fi.dto.AuthenticationResponseDTO;
import com.data.fi.util.JwtUtil;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    AuthController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService; 
    }

    @PostMapping("/authenticate")
    public AuthenticationResponseDTO createAuthenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequest) throws Exception {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return new AuthenticationResponseDTO(jwt);
    }
}
