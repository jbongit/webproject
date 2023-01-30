package com.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.model.Registration;
public interface ProjectDAO extends JpaRepository<Registration, Integer>{

	public Registration findByEmailid(String email_id);

}