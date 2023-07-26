package com.ZioSet.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ZioSet.model.Asset;

public interface AssetRepo extends JpaRepository<Asset, Integer>,AssetCustomeRepo{

	
}
