package com.data.fi.dto;

public class PasswordRecoveryResponseDTO {
    private String message;
    
    
	public PasswordRecoveryResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
