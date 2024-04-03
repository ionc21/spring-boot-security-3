package com.pluralsight.security.repository;

import java.util.List;
import com.pluralsight.security.entity.SupportQuery;
import org.springframework.data.repository.CrudRepository;

public interface SupportQueryRepository extends CrudRepository<SupportQuery, String> {
	
	List<SupportQuery> findByUsername(String username);
	List<SupportQuery> findAll();
}
