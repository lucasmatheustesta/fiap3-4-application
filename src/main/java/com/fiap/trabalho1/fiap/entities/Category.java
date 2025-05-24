package com.fiap.trabalho1.fiap.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {

	@Id
	private UUID id;
	private String name;
    private String type;

    @OneToMany(fetch = FetchType.EAGER,
    		cascade = CascadeType.ALL,
    		mappedBy = "category")
    private List<Product> products;
    
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
