package com.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.model.Registration;

public interface ProjectDAO extends JpaRepository<Registration, Integer>{

	public Registration findByEmailid(String email_id);
	
	public List<Registration> findAllByRole(String role);

	public List<Registration> findAllByStatus(String status);

	public List<Registration> findByOTPIsNotNull();

	public List<Registration> findAllByNameContaining(String searchQuery);

}