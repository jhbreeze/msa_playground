package com.sparta.msa_exam.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.msa_exam.auth.entity.User;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
