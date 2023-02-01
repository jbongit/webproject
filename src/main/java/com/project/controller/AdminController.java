package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.dao.ProjectDAO;
import com.project.model.Registration;


@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
	
	@Autowired
	ProjectDAO projectDAO;
	
	
	@GetMapping("/dashboard")
	public String viewDashboard(Model model) {
		List<Registration> userList=projectDAO.findAll();
		model.addAttribute("userList",userList);
		return "AdminDashboard";
	}
}
