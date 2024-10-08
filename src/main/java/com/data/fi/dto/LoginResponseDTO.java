package com.data.fi.dto;

public class LoginResponseDTO {
    private String token;
    private String email;
    
    
    

    public LoginResponseDTO(String token, String email) {
		super();
		this.token = token;
		this.email = email;
	}



    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }
}
