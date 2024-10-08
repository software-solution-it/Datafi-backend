package com.data.fi.dto;

public class PasswordRecoveryRequestDTO {
	
    private String email;

    public PasswordRecoveryRequestDTO() {}

    public PasswordRecoveryRequestDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
