package com.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.apache.catalina.mapper.Mapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.controller.ProjectController;
import com.project.dao.ProjectDAO;
import com.project.model.Registration;
import com.project.service.ProjectService;

import jakarta.mail.Session;
import jakarta.persistence.Temporal;
import jakarta.servlet.http.HttpSession;

@SpringBootTest

class WebProjectApplicationTests {

	@InjectMocks
	private ProjectService projectService;

	@Mock
	private ProjectDAO projectDAO;

	@Mock
	private JavaMailSender mailSender;

	@Mock
	private HttpSession httpSession;

	@Mock
	private Registration user,newuser;

	@Test
	public void testSendMailMethod() {
		// Setup
		String from = "webproject@developer.com";
		String to = "newuser@gotgel.org";
		String subject = "Test";
		String content = "Test";
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		doNothing().when(mailSender).send(message);

		// Execute
		projectService.sendMail(to, subject, content);

		// Verifying
		verify(mailSender, times(1)).send(message);
	}

	@Test
	public void testScheduleTask() {
		// SetUP
//		Registration newuser = new Registration();
//		newuser.setName("Test");
//		newuser.setApproverName("Test");
//		newuser.setEmailid("Test");
//		newuser.setMobileno("1234567890");
//		newuser.setStatus("PENDING");
//		newuser.setPassword("Test");
//		newuser.setRegistrationdateTime(LocalDateTime.now().minusHours(2));
//
//		Registration newuser1 = new Registration();
//		newuser1.setName("Test");
//		newuser1.setApproverName("Test");
//		newuser1.setEmailid("Test");
//		newuser1.setMobileno("1234567890");
//		newuser1.setStatus("PENDING");
//		newuser1.setPassword("Test");
//		newuser1.setRegistrationdateTime(LocalDateTime.now());
		
		when(user.getRegistrationdateTime()).thenReturn(LocalDateTime.now().minusHours(2));
		when(newuser.getRegistrationdateTime()).thenReturn(LocalDateTime.now());
		List<Registration> l = List.of(user,newuser);
		when(projectDAO.findAllByStatus("PENDING")).thenReturn(l);
		// Execute
		projectService.AutoApprovalUser();

		// Verifying
		verify(projectDAO, times(1)).findAllByStatus("PENDING");
		// verify(projectDAO,times(1)).save(user);

	}

	@Test
	public void testSendOTP() {
		//if(user is not null)
		when(projectDAO.findByEmailid("newuser@gotgel.org")).thenReturn(user);
		//Execute
		projectService.sendOTP("newuser@gotgel.org", httpSession);
		
		//Verifying
		verify(projectDAO,times(1)).findByEmailid("newuser@gotgel.org");
		
		verify(projectDAO,times(1)).save(user);
		
	}

	@Test
	public void testSendOTPelse() {
		// Execute
		projectService.sendOTP("newuser@gotgel.org", httpSession);

		// Verifying
		verify(projectDAO, times(1)).findByEmailid("newuser@gotgel.org");

	}

	@Test
	public void testAutoRemovalOTP() {
		when(projectDAO.findByOTPIsNotNull()).thenReturn(List.of(user));
		projectService.AutoRemovalOTP();
	}

}
