package com.bank.repository;

import java.util.Optional;

import com.bank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	User findByEmail(String email);

	Boolean existsByEmail(String email);

}
