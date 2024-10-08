package com.data.fi.dto;

public class AuthenticationResponseDTO {
    private String jwt;
    
    

	public AuthenticationResponseDTO(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
