package com.kati.core.domain.allergy.service;

import java.util.List;

import com.kati.core.domain.allergy.domain.UserAllergy;
import com.kati.core.global.config.security.auth.PrincipalDetails;

public interface AllergyService {

	public UserAllergy saveUserAllergy(PrincipalDetails principal, List<String> allergyList);

	public List<String> findUserAllergy(PrincipalDetails principal);

}
