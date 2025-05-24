package com.fiap.trabalho1.fiap.infrastructure.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class BusinessValidationException extends RuntimeException {

	private Map<String, List<String>> fieldErrors;
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	public BusinessValidationException() {}
	
	public BusinessValidationException(String stringCode) {
		super(stringCode);
	}
	
	public BusinessValidationException(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public BusinessValidationException(String fieldName, String fieldError) {
		this.addFieldErrors(fieldName, fieldError);
	}
	
	public void addFieldErrors(String field, String error) {
		if (this.fieldErrors == null) {
			this.fieldErrors = new HashMap<>();
		}
		
		List<String> errors = this.fieldErrors.get(field);
		
		if (errors == null) {
			errors = new ArrayList<>();
			this.fieldErrors.put(field, errors);
		}
		
		errors.add(error);
	}
	
	public void throwIfExistsErrors() {
		if (getMessage() != null || (this.fieldErrors != null && !this.fieldErrors.isEmpty())) {
			throw this;
		}
	}

	public void fieldNotNull(String fieldName, String value) {
		this.fieldValidate(fieldName, value != null, "is_empty");
	}

	public void fieldValidate(String fieldName, boolean isValid, String message) {
		if (!isValid) {
			this.addFieldErrors(fieldName, message);
		}
	}
	
	public String toJsonString() {
		JSONObject errorResponse = new JSONObject();
		
		if (getMessage() != null) {
			errorResponse.put("errors", Arrays.asList(getMessage()));
		}
		
		if (this.fieldErrors != null) {
			errorResponse.put("fieldErrors", new JSONObject(fieldErrors));
		}
		
		return errorResponse.toString();
	}
	
	@Override
	public String toString() {
		return this.toJsonString();
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
}
