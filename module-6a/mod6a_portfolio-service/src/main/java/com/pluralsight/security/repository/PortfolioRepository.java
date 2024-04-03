package com.pluralsight.security.repository;

import com.pluralsight.security.entity.Portfolio;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PortfolioRepository extends CrudRepository<Portfolio, String> {
	
	Optional<Portfolio> findByUserId(String username);
	boolean existsByUserId(String username);
}
