package com.api.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	 Optional<User> findByEmail(String email);
	 Optional<User> findByUname(String uname);
}
