package com.project.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.config.CustomUserDetails;
import com.project.dao.ApproverDAO;
import com.project.dao.ProjectDAO;
import com.project.model.Approver;
import com.project.model.Registration;

import jakarta.servlet.http.HttpServletRequest;


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
	
	@GetMapping("/report")
	public ResponseEntity<byte[]> generateReport() throws IOException {
		List<Registration> users=projectDAO.findAll();
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet=workbook.createSheet("Report");
		HSSFRow header=sheet.createRow(0);
		header.createCell(0).setCellValue("ID");
		header.createCell(1).setCellValue("Name");
		header.createCell(2).setCellValue("Email ID");
		header.createCell(3).setCellValue("Mobile No");
		header.createCell(4).setCellValue("Registration DATE");
		header.createCell(5).setCellValue("Registration TIME");
		
		for(int i=0;i<users.size();i++) {
			HSSFRow row=sheet.createRow(i+1);
			row.createCell(0).setCellValue(users.get(i).getId());
			row.createCell(1).setCellValue(users.get(i).getName());
			row.createCell(2).setCellValue(users.get(i).getEmailid());
			row.createCell(3).setCellValue(users.get(i).getMobileno());
			row.createCell(4,CellType.STRING).setCellValue(users.get(i).getRegistrationdateTime().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			row.createCell(5,CellType.STRING).setCellValue(users.get(i).getRegistrationdateTime().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
		}
		
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		workbook.write(baos);
		
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
		headers.setContentDispositionFormData("attachment", "report.xls");
		headers.setCacheControl("must-validate,post-check=0,pre-check=0");
		
		return new ResponseEntity<>(baos.toByteArray(),headers,HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public String searchHandler(@RequestParam("searchQuery") String searchQuery,Model model,RedirectAttributes redirectAttributes,HttpServletRequest request) {
		List<Registration> userList=projectDAO.findAllByNameContaining(searchQuery);
		model.addAttribute("userList",userList);
		return "AdminDashboard";
	}
	
}
