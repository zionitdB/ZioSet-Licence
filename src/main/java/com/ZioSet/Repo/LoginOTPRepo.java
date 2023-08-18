package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.LoginOTP;

public interface LoginOTPRepo extends JpaRepository<LoginOTP, Integer> {
	@Query("From LoginOTP l where l.userName=?1")
	Optional<LoginOTP> getByUserName(String username);

}
