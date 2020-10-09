package com.cffilter.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cffilter.backend.models.ERole;
import com.cffilter.backend.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role , Long> {
	Optional<Role> findByName(ERole name);
}
