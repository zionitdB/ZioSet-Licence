package com.ZioSet.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ZioSet.model.Bundle;

public interface BundleRepo extends JpaRepository<Bundle, Integer>,BundleCustomeRepo {

	

}
