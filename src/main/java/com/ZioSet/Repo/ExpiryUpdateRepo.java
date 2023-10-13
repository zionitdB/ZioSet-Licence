package com.ZioSet.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ZioSet.model.ExpiryUpdate;

public interface ExpiryUpdateRepo  extends JpaRepository<ExpiryUpdate, Integer> ,ExpiryUpdateCustomeRepo{

	

}
