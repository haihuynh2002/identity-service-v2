package com.devteria.identity_service.repository;

import com.devteria.identity_service.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
}
