package com.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.Approver;

public interface ApproverDAO extends JpaRepository<Approver, Integer> {

}