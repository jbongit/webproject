package com.project.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.config.CustomUserDetails;
import com.project.dao.ApproverDAO;
import com.project.dao.ProjectDAO;
import com.project.model.Approver;
import com.project.model.Registration;


@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
	
	@Autowired
	ProjectDAO projectDAO;
	
	@Autowired
	ApproverDAO approverDAO;
	
	@GetMapping("/dashboard")
	public String viewDashboard(Model model) {
		List<Registration> userList=projectDAO.findAllByRole("ROLE_USER");
		model.addAttribute("userList",userList);
		return "AdminDashboard";
	}
	
	@GetMapping("/pending-approvals")
	public String viewPendingApprovals(Model model) {
		List<Registration> userList=projectDAO.findAllByStatus("Pending");
		model.addAttribute("userList",userList);
		return "PendingApprovals";
	}
	
	@GetMapping("/user/{id}")
	public String showUserInfo(Model model,@PathVariable("id") Integer id) {

		Registration user = projectDAO.findById(id).orElse(new Registration());

		model.addAttribute("user", user);
		return "UserApprovalProfile";
	}
	
	@GetMapping("/user/{id}/approved")
	public String ApproveUser(Model model,@PathVariable("id") Integer id,Principal principal) {

		Registration user = projectDAO.findById(id).orElse(new Registration());
		user.setStatus("APPROVED");
		projectDAO.save(user);
		
		
		// Saving Approval Log in Approver Info Table in Database
		Approver ainfo=new Approver();
		ainfo.setUseremailid(user.getEmailid());
		ainfo.setUsername(user.getName());
		
		LocalDateTime currentTime=LocalDateTime.now();
		ainfo.setDateTime(currentTime);
		
		CustomUserDetails auth=(CustomUserDetails)((Authentication)principal).getPrincipal();
		
		ainfo.setApproveremailid(auth.getUsername());
		ainfo.setApprovername(auth.getName());
		ainfo.setStatus("APPROVED");
		
		approverDAO.save(ainfo);
		
		
		return "redirect:/admin/pending-approvals";
	}
	
	@GetMapping("/user/{id}/rejected")
	public String RejectUser(Model model,@PathVariable("id") Integer id,Principal principal) {

		Registration user = projectDAO.findById(id).orElse(new Registration());
		user.setStatus("REJECTED");
		projectDAO.save(user);
		
		// Saving Approval Log in Approver Info Table in Database
		Approver ainfo=new Approver();
		ainfo.setUseremailid(user.getEmailid());
		ainfo.setUsername(user.getName());
		
		LocalDateTime currentTime=LocalDateTime.now();
		ainfo.setDateTime(currentTime);
		
		CustomUserDetails auth=(CustomUserDetails)((Authentication)principal).getPrincipal();
		
		ainfo.setApproveremailid(auth.getUsername());
		ainfo.setApprovername(auth.getName());
		ainfo.setStatus("REJECTED");
		
		approverDAO.save(ainfo);
		
		
		return "redirect:/admin/pending-approvals";
	}
}
