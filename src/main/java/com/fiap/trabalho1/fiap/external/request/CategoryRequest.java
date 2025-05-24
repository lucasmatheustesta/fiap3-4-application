package com.fiap.trabalho1.fiap.external.request;

import java.util.UUID;

public class CategoryRequest {

	private UUID idCateogry;
	private String name;
    private String type;
    
	public UUID getIdCateogry() {
		return idCateogry;
	}
	
	public void setIdCateogry(UUID idCateogry) {
		this.idCateogry = idCateogry;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
    
}
