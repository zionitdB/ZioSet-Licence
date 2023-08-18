package com.ZioSet.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ZioSet.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>,CategoryCustomeRepo {

	
}
