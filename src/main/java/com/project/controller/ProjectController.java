package com.project.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.dao.ProjectDAO;
import com.project.model.Registration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProjectController {

	@Autowired
	ProjectDAO projectDAO;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/registration")
	public String registraitonView() {
		return "Registration";
	}

	@PostMapping("/saveUser")
	public String saveUserInfo(Model model,Registration user, @RequestParam("profileImage") MultipartFile file,HttpServletRequest request) {

		try {
			user.setRole("ROLE_USER");
			System.out.println(user.getPassword());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			if (file.isEmpty()) {
				System.out.println("File Empty");
			} else {
				user.setImageURL(user.getEmailid()+"_"+file.getOriginalFilename());
				Path path=Paths.get("C:/STS Workspace NEW/Main Workspace/WebProject/src/main/resources/static/img/"+user.getEmailid()+"_"+file.getOriginalFilename());
				Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
				HttpSession session=request.getSession();
				session.setAttribute("message", "Registration Successfull For "+user.getName());
			}
			projectDAO.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/registration";
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/user/{username}")
	public String showUserInfo(Model model,@PathVariable("username") String email_id) {
		
		Registration user=projectDAO.findByEmailid(email_id);
		
		model.addAttribute("user",user);
		return "UserDashboard";
	}
	
	
	@GetMapping("/login")
	public String loginInfo() {
		return "Login";
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/user/{id}/update")
	public String updateView(Model model,@PathVariable("id") int id) {
		
		Registration user=projectDAO.findById(id).orElse(new Registration());
		model.addAttribute("user",user);
		return "UpdateDetails";
	}
	
	@PostMapping("/updateUser")
	public String updateUserInfo(Model model,Registration user, @RequestParam("profileImage") MultipartFile file,HttpServletRequest request,@RequestParam("id") int id) {
		Registration existingUser=projectDAO.findById(id).orElse(new Registration());
		try {
			if (file.isEmpty()) {
				System.out.println("File Empty");
				user.setImageURL(existingUser.getImageURL());
			} else {
				user.setImageURL(user.getEmailid()+"_"+file.getOriginalFilename());
				
				//Deleting
				Path existingPath=Paths.get("C:/STS Workspace NEW/Main Workspace/WebProject/src/main/resources/static/img/"+existingUser.getImageURL());
				Files.deleteIfExists(existingPath);
				
				//Copy
				Path path=Paths.get("C:/STS Workspace NEW/Main Workspace/WebProject/src/main/resources/static/img/"+user.getEmailid()+"_"+file.getOriginalFilename());
				Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
			}
			HttpSession session=request.getSession();
			session.setAttribute("success", "Updation Successfull For "+user.getName());
			System.out.println(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setRole(existingUser.getRole());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		projectDAO.save(user);
		return "redirect:user/"+user.getId()+"/update";
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/user/{id}/delete")
	public String deleteUserInfo(@PathVariable("id") int id) {
		Registration existingUser=projectDAO.findById(id).orElse(new Registration());
		Path existingPath=Paths.get("C:/STS Workspace NEW/Main Workspace/WebProject/src/main/resources/static/img/"+existingUser.getImageURL());
		try {
			Files.deleteIfExists(existingPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		projectDAO.delete(existingUser);
		return "redirect:/logout";
	}
}