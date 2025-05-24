package com.fiap.trabalho1.fiap.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Category {

	@Id
	private UUID id;
	private String name;
    private String type;

	public Category() {}
    
    public Category(UUID idCateogry, String name, String type) {
    	this.id = idCateogry;
    	this.name = name;
    	this.type = type;
    }
    
	public UUID getIdCateogry() {
		return id;
	}
	
	public void setIdCateogry(UUID idCateogry) {
		this.id = idCateogry;
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
