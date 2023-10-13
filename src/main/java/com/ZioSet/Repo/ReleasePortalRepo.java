package com.ZioSet.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ZioSet.model.Licence;
import com.ZioSet.model.ReleasePortal;

public interface ReleasePortalRepo extends JpaRepository<ReleasePortal, Integer>,ReleasePortalCustomeRepo{

	

}
