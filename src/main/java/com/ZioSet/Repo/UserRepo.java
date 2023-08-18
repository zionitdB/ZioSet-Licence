package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.UserInfo;

public interface UserRepo extends JpaRepository<UserInfo, Integer>,UserCustomeRepo {
	@Query("From UserInfo u where u.username=?1")
	Optional<UserInfo> getUsersByUsername(String username);

	

}
