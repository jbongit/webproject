package com.project.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.dao.ProjectDAO;
import com.project.model.Registration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

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
	public String saveUserInfo(@Valid Registration user, BindingResult bindingResult,
			@RequestParam("profileImage") MultipartFile file, HttpServletRequest request, Model model,RedirectAttributes redirectAttributes) {
		try {
			if (bindingResult.hasErrors()) {
				List<FieldError> errors = bindingResult.getFieldErrors();
				for (FieldError error : errors) {
					String fieldName=error.getField();
					String errorMessage=error.getDefaultMessage();
					redirectAttributes.addFlashAttribute(fieldName+"Error",errorMessage);
//					System.out.println(fieldName+"Error "+errorMessage);
				}

				return "redirect:/registration";
			}
			user.setRole("ROLE_USER");
			System.out.println(user.getPassword());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			if (file.isEmpty()) {
				System.out.println("File Empty");
			} else {
				user.setImageURL(user.getEmailid() + "_" + file.getOriginalFilename());
				Path path = Paths.get("C:/STS Workspace NEW/Main Workspace/WebProject/src/main/resources/static/img/"
						+ user.getEmailid() + "_" + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				HttpSession session = request.getSession();
				session.setAttribute("message", "Registration Successfull For " + user.getName());
			}
			projectDAO.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/registration";
	}

	@PreAuthorize("hasAuthority('ROLE_USER') and #id==authentication.principal.id")
	@GetMapping("/user/{id}")
	public String showUserInfo(Model model,@PathVariable("id") Integer id) {

		Registration user = projectDAO.findById(id).orElse(new Registration());

		model.addAttribute("user", user);
		return "UserDashboard";
	}

	/*
	 * @PreAuthorize("hasAuthority('ROLE_USER')")
	 * 
	 * @GetMapping("/user/{username}") public String newMethod(Model
	 * model,@PathVariable("username") String emailid) {
	 * 
	 * Registration user = projectDAO.findByEmailid(emailid);
	 * 
	 * model.addAttribute("user", user); return "UserDashboard"; }
	 */
	@GetMapping("/login")
	public String loginInfo() {
		return "Login";
	}

	@PreAuthorize("hasAuthority('ROLE_USER') and #id==authentication.principal.id")
	@GetMapping("/user/{id}/update")
	public String updateView(Model model, @PathVariable("id") int id) {

		Registration user = projectDAO.findById(id).orElse(new Registration());
		model.addAttribute("user", user);
		return "UpdateDetails";
	}

	@PostMapping("/updateUser")
	public String updateUserInfo(@Valid Registration user,BindingResult bindingResult, @RequestParam("profileImage") MultipartFile file,
			HttpServletRequest request, @RequestParam("id") int id,Model model,RedirectAttributes redirectAttributes) {
		Registration existingUser = projectDAO.findById(id).orElse(new Registration());
		try {
			if (bindingResult.hasErrors()) {
				List<FieldError> errors = bindingResult.getFieldErrors();
				for (FieldError error : errors) {
					String fieldName=error.getField();
					String errorMessage=error.getDefaultMessage();
					redirectAttributes.addFlashAttribute(fieldName+"Error",errorMessage);
				System.out.println(fieldName+"Error "+errorMessage);
				}
				redirectAttributes.addFlashAttribute("user", existingUser);
				return "redirect:user/" + user.getId() + "/update";
			}
			
			if (file.isEmpty()) {
				System.out.println("File Empty");
				existingUser.setImageURL(existingUser.getImageURL());
			} else {

				// Deleting
				Path existingPath = Paths
						.get("C:/STS Workspace NEW/Main Workspace/WebProject/src/main/resources/static/img/"
								+ existingUser.getImageURL());
				Files.deleteIfExists(existingPath);
				
				existingUser.setImageURL(user.getEmailid() + "_" + file.getOriginalFilename());

				// Copy
				Path path = Paths.get("C:/STS Workspace NEW/Main Workspace/WebProject/src/main/resources/static/img/"
						+ user.getEmailid() + "_" + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			HttpSession session = request.getSession();
			session.setAttribute("success", "Updation Successfull For " + user.getName());
			System.out.println(user);

		} catch (Exception e) {
			e.printStackTrace();
		}
		existingUser.setName(user.getName());
		existingUser.setEmailid(user.getEmailid());
		existingUser.setMobileno(user.getMobileno());
		projectDAO.save(existingUser);
		return "redirect:user/" + user.getId() + "/update";
	}

	@PreAuthorize("hasAuthority('ROLE_USER') and #id==authentication.principal.id")
	@GetMapping("/user/{id}/delete")
	public String deleteUserInfo(@PathVariable("id") int id) {
		Registration existingUser = projectDAO.findById(id).orElse(new Registration());
		Path existingPath = Paths.get("C:/STS Workspace NEW/Main Workspace/WebProject/src/main/resources/static/img/"
				+ existingUser.getImageURL());
		try {
			Files.deleteIfExists(existingPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		projectDAO.delete(existingUser);
		return "redirect:/logout";
	}
	
	@GetMapping("/test")
	public String gettest() {
		return "test";
	}
	
}