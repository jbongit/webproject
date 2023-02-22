package com.project.controller;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.dao.ProjectDAO;
import com.project.model.Registration;
import com.project.service.ProjectService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ProjectController {

	@Autowired
	ProjectDAO projectDAO;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired ProjectService projectService;
	
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
				redirectAttributes.addFlashAttribute("user",user);
				
				return "redirect:/registration";
			}
			
			if(projectDAO.findByEmailid(user.getEmailid()) != null) {
				redirectAttributes.addFlashAttribute("user",user);
				redirectAttributes.addFlashAttribute("emailidError","Email Already Exists");
				return "redirect:/registration";
			}
			
			user.setStatus("PENDING");
			user.setRole("ROLE_USER");
			System.out.println(user.getPassword());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			//if (file.isEmpty()!=true){
				user.setImageURL(user.getEmailid() + "_" + file.getOriginalFilename());
				Path path = Paths.get("C:/STS Workspace NEW/Main Workspace/WebProject/src/main/resources/static/img/"
						+ user.getEmailid() + "_" + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			//}
			LocalDateTime currentTime=LocalDateTime.now();
			user.setRegistrationdateTime(currentTime);
			projectDAO.save(user);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("message", "Registration Successfull For " + user.getName());
		
		String newMessage="Congratulations "+user.getName()+" ,"+System.lineSeparator()+
				"You Are Successfully Registered."+System.lineSeparator()
				+"Regards ,"+System.lineSeparator()+"Web Dev Project Team";
		projectService.sendMail(user.getEmailid(),"Registration Successful", newMessage);
		System.out.println("Mail Sent to "+user.getEmailid());
		return "redirect:/registration";
	}

	@PreAuthorize("hasAuthority('ROLE_USER') and #id==authentication.principal.id")
	@GetMapping("/user/{id}")
	public String showUserInfo(Model model,@PathVariable("id") Integer id){
//		if(!authentication.getCredentials().equals(id.toString())) {
//			throw new AccessDeniedException(authentication.getCredentials().toString());
//		}
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
	
	@GetMapping("/forgotpassword")
	public String ForgotPass() {
		return "ForgotPass";
	}
	
	@GetMapping("/sendOTP")
	public String sendOTP(HttpServletRequest request,@RequestParam("emailid") String emailid,RedirectAttributes redire){
		HttpSession session=request.getSession();
		projectService.sendOTP(emailid, session);
		redire.addFlashAttribute("emailid",emailid);
		return "redirect:/forgotpassword";
	}
	
	@PostMapping("/verify")
	public String verifyOTP(@RequestParam("emailid") String emailid ,@RequestParam("otp") String otp,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		HttpSession session=request.getSession();
		Registration user=projectDAO.findByEmailid(emailid);
		if(user==null) {
			redirectAttributes.addFlashAttribute("error","Incorrect Details");
			return "redirect:/forgotpassword";
		}else {
			if(user.getOTP()== null) {
				redirectAttributes.addFlashAttribute("error","Incorrect OTP");
				redirectAttributes.addFlashAttribute("emailid",emailid);
				return "redirect:/forgotpassword";
			}
			if(user.getOTP().equals(otp)) {
				session.setAttribute("OTPmessage","Successfully Verified");
				session.setAttribute("emailid",emailid);
				session.setAttribute("enteredOTP",otp);
				return "redirect:/resetpassword";
			}
			else {
				session.setAttribute("OTPmessage","Incorrect OTP");
				redirectAttributes.addFlashAttribute("emailid",emailid);
				return "redirect:/forgotpassword";
			}
		}
	}
	
	@GetMapping("/resetpassword")
	public String resetPassword() {
		return "ResetPassword";
	}
	
	@PostMapping("/change-password")
	public String changePassword(HttpServletRequest request,RedirectAttributes redirectAttributes,@RequestParam("password") String password) 
	{
		HttpSession session=request.getSession();
		Registration user=projectDAO.findByEmailid((String)session.getAttribute("emailid"));
		String otp=(String)session.getAttribute("enteredOTP");
		if(user.getOTP().equals(otp)) {
			user.setPassword(passwordEncoder.encode(password));
			projectDAO.save(user);
			session.setAttribute("pwchanged", "Password Successfully Changed");
			projectService.sendMail(user.getEmailid(), "Password Change Confirmation Email", "Congratulations , You Have Successfully Changed Your Password");
			return "redirect:/login";
		}
		
		return "redirect:/login";
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER') and #id==authentication.principal.id")
	@GetMapping("/user/{id}/changepassword")
	public String changeOldPassword(@PathVariable("id") int id,Model model) {
		Registration user = projectDAO.findById(id).orElse(new Registration());
		model.addAttribute("user", user);
		return "ChangePassword";
	}
	
	@PostMapping("change-old-password")
	public String submitNewPassword(@RequestParam("id") int id,@RequestParam("old-password") String old,@RequestParam("new-password") String newpass,@RequestParam("confirm-password") String confirm,RedirectAttributes redirectAttribute){
		Registration existingUser = projectDAO.findById(id).orElse(new Registration());
		
		if(old.length()==0 || newpass.length()==0 || confirm.length()==0) {
			redirectAttribute.addFlashAttribute("error","Field Cannot Be Empty");
			return "redirect:/user/"+existingUser.getId()+"/changepassword";
		}
		
		if(BCrypt.checkpw(old,existingUser.getPassword())) {
			if(newpass.equals(confirm)){
				existingUser.setPassword(passwordEncoder.encode(newpass));
				projectDAO.save(existingUser);
				redirectAttribute.addFlashAttribute("error","Password Successfully Changed");
				return "redirect:/user/"+existingUser.getId();
			}else {
				redirectAttribute.addFlashAttribute("error","Confirm Password Does Not Match");
				return "redirect:/user/"+existingUser.getId()+"/changepassword";
			}
		}else {
			redirectAttribute.addFlashAttribute("error","Old Password Does Not Match");
			return "redirect:/user/"+existingUser.getId()+"/changepassword";
		}
	}
	
}