package com.fiap.trabalho1.fiap.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderStatus {

	RECEIVED("RECEIVED"),
	PREPARATION("PREPARATION"),
	READY("READY"),
	FINISHED("FINISHED");
	
	private String status;
	
	OrderStatus(String status) {
		this.status = status;
	}

	@JsonValue
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
