package com.data.fi.dto;

public class UserResponseDTO {
    private Long id;
    private String email;
    private String role;
    private boolean emailConfirmed;
    
    
    
    
	public UserResponseDTO(Long id, String email, String role, boolean emailConfirmed) {
		super();
		this.id = id;
		this.email = email;
		this.role = role;
		this.emailConfirmed = emailConfirmed;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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

