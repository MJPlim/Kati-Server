package com.kati.core.domain.allergy.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class CreateUserAllergyRequest {
	private List<String> allergyList;
}
