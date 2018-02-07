package io.github.efrem.springsecurityrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.efrem.springsecurityrest.domain.ApplicationUser;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
	
	ApplicationUser findByLogin(String login);

}
