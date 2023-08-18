package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.UserLoginLogs;

public interface UserLoginLogsRepo extends JpaRepository<UserLoginLogs, Integer>{
	@Query("From UserLoginLogs u where u.userName=?1")
	Optional<UserLoginLogs> getUserLoginLogByUserNameAndIP(String username);

}
