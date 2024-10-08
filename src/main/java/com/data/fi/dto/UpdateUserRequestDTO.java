package com.data.fi.dto;

public class UpdateUserRequestDTO {
    private String email;
    private String password;
    private String role;
    private boolean emailConfirmed;
    
    
    
	public UpdateUserRequestDTO(String email, String password, String role, boolean emailConfirmed) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
		this.emailConfirmed = emailConfirmed;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEmailConfirmed() {
		return emailConfirmed;
	}
	public void setEmailConfirmed(boolean emailConfirmed) {
		this.emailConfirmed = emailConfirmed;
	}

    
    
}
