package org.jsp.reservation_api.repository;

import java.util.Optional;

import org.jsp.reservation_api.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	Optional<Admin> findByPhoneAndPassword(long phone, String password);

	Optional<Admin> findByEmailAndPassword(String email, String password);

	Optional<Admin> findByToken(String token);
	
	Optional<Admin> findByEmail(String email);
}
