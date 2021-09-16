package com.kati.core.domain.allergy.repository;

import com.kati.core.domain.allergy.domain.AllergyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergyCategoryRepository extends JpaRepository<AllergyCategory, Integer>{
	public AllergyCategory findByAllergyName(String allergyName);
}
