package com.kati.core.domain.allergy.exception;

public class DuplicatedCreateUserAllergyException extends IllegalArgumentException {
	
	public DuplicatedCreateUserAllergyException(AllergyExceptionMessage m) {
		super(m.getMessage());
	}
}
