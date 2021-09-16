package com.kati.core.domain.allergy.repository;

import java.util.Optional;

import com.kati.core.domain.allergy.domain.FoodAllergy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodAllergyRepository extends JpaRepository<FoodAllergy, Integer> {
	public Optional<FoodAllergy> findByAllergyMaterial(String allergyMaterial);
}
