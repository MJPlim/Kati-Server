package com.kati.core.domain.api.repository;

import com.kati.core.domain.api.domain.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {
    List<ApiKey> findAllByKeyName(String keyName);
}
