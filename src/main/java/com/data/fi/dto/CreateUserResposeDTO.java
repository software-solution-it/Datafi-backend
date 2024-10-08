package com.data.fi.dto;

public class CreateUserResposeDTO {
    private Long id;
    private String email;
    
    
	public CreateUserResposeDTO(String email, long id) {
		super();
		this.id = id;
		this.email = email;
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
}
