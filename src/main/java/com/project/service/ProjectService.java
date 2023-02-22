package com.project.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.project.dao.ProjectDAO;
import com.project.model.Registration;

import jakarta.servlet.http.HttpSession;

@Service
public class ProjectService {

	@Autowired
	ProjectDAO projectDAO;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendMail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("webproject@developer.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
		System.out.println("Mail Sent Successfully");
	}
	
	@Scheduled(fixedRate = 60 * 1000)
	public void AutoApprovalUser() {
		List<Registration> users = projectDAO.findAllByStatus("PENDING");
		LocalDateTime currentTime=LocalDateTime.now();
		for(Registration user : users) {
			if(Duration.between(user.getRegistrationdateTime(), currentTime).toHours()>=1) {
				user.setStatus("APPROVED");
				user.setApproverName("SYSTEM");
				projectDAO.save(user);
			}
		}
	}
	
	public void sendOTP(String emailid,HttpSession session) {
		Registration user=projectDAO.findByEmailid(emailid);
		if(user==null) {
			session.setAttribute("OTPmessage", "OTP Sent");
		}else {
			String newOTP=generateOTP();
			user.setOTP(newOTP);
			projectDAO.save(user);
			String message="Your OTP for verification is : "+user.getOTP()+System.lineSeparator()+"GENERATED OTP WILL BE AUTOMATICALLY EXPIRED IN 5 MINUTES";
			sendMail(emailid,"OTP Verification",message);
			session.setAttribute("OTPmessage","OTP Sent");
		}
	}
	
	private String generateOTP() {
		return String.valueOf((int)(Math.random()*900000)+1000000);
	}
	
	
	@Scheduled(fixedRate = 5 * 60 * 1000)
	public void AutoRemovalOTP() {
		List<Registration> users = projectDAO.findByOTPIsNotNull();
		for(Registration user : users) {
			user.setOTP(null);
			projectDAO.save(user);
		}
	}

}
