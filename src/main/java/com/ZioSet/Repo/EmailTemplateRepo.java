package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.EmailTemplate;

public interface EmailTemplateRepo extends JpaRepository<EmailTemplate, Integer>{
	@Query("From EmailTemplate e where e.templateFor=?1")
	Optional<EmailTemplate> getEmailTemplateFor(String templateFor);

}
