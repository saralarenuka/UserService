package com.api.repo.test;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.User;

public interface UserRepositoryTest extends JpaRepository<User, Integer> {

}
