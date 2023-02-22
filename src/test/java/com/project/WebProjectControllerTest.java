package com.project;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.project.config.CustomUserDetails;
import com.project.dao.ProjectDAO;
import com.project.model.Registration;

import jakarta.servlet.http.HttpSession;

@SpringBootTest
@AutoConfigureMockMvc
public class WebProjectControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private BindingResult bindingResult;

	@Mock
	private Registration user;

	@MockBean
	private ProjectDAO projectDAO;

	@MockBean
	private JavaMailSender mailSender;

	@Mock
	private FileInputStream fis;

	@Mock
	private MockMultipartFile file;

	@Mock
	MockHttpSession httpSession;

	@Test
	public void testRegistrationPage() throws Exception {
		mockMvc.perform(get("/registration")).andExpect(status().isOk()).andExpect(view().name("Registration"));
	}

	@Test
	public void testSavePerson() throws Exception {
		when(user.getName()).thenReturn("Example");
		when(user.getEmailid()).thenReturn("debnmxcm.mo@nmusennr.com");
		when(user.getPassword()).thenReturn("Example");
		when(user.getMobileno()).thenReturn("1234567890");
		when(projectDAO.findByEmailid(user.getEmailid())).thenReturn(user);
		//doNothing().when(mailSender).send(any(SimpleMailMessage.class));;
		//		doNothing().when(projectDAO.save(any(Registration.class)));
		FileInputStream fis = new FileInputStream(
				"C:/Users/jalaj.bhandula/OneDrive - HCL Technologies Ltd/Pictures/Screenshots/Screenshot (50).png");
		MockMultipartFile multipartFile = new MockMultipartFile("profileImage", fis);
		MvcResult result =mockMvc.perform(multipart("/saveUser")
				.file(multipartFile)
				.param("name", user.getName())
				.param("password", user.getPassword())
				.param("emailid",user.getEmailid())
				.param("mobileno",user.getMobileno())
				.contentType(MediaType.MULTIPART_FORM_DATA)
				)
				.andExpect(status().is3xxRedirection())
				.andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
		
	}

	@Test
	public void testSavePersonhasErrors() throws Exception{
		when(user.getName()).thenReturn("");
		//when(bindingResult.hasErrors()).thenReturn(true);
		when(user.getEmailid()).thenReturn("debnmmo@nmusennr.com");
		when(user.getPassword()).thenReturn("Example");
		when(user.getMobileno()).thenReturn("1234567890");
		//when(model).thenReturn(new ExtendedModelMap());
		
//		doNothing().when(projectDAO.save(any(Registration.class)));
		FileInputStream fis = new FileInputStream(
				"C:/Users/jalaj.bhandula/OneDrive - HCL Technologies Ltd/Pictures/Screenshots/Screenshot (50).png");
		MockMultipartFile multipartFile = new MockMultipartFile("profileImage", fis);
		MvcResult result =mockMvc.perform(multipart("/saveUser")
				.file(multipartFile)
				.param("name", user.getName())
				.param("password", user.getPassword())
				.param("emailid",user.getEmailid())
				.param("mobileno",user.getMobileno())
				.contentType(MediaType.MULTIPART_FORM_DATA)
				)
				.andExpect(status().is3xxRedirection())
				.andReturn();
	}

	@Test
	public void testSave() throws Exception{
		when(user.getName()).thenReturn("New");
		when(user.getEmailid()).thenReturn("demo@gotgel.org");
		when(user.getPassword()).thenReturn("Example");
		when(user.getMobileno()).thenReturn("1234567890");
		
		FileInputStream fis = new FileInputStream(
				"C:/Users/jalaj.bhandula/OneDrive - HCL Technologies Ltd/Pictures/Screenshots/Screenshot (50).png");
		MockMultipartFile multipartFile = new MockMultipartFile("profileImage", fis);
		MvcResult result =mockMvc.perform(multipart("/saveUser")
				.file(multipartFile)
				.param("name", user.getName())
				.param("password", user.getPassword())
				.param("emailid",user.getEmailid())
				.param("mobileno",user.getMobileno())
				.contentType(MediaType.MULTIPART_FORM_DATA)
				)
				.andExpect(status().is3xxRedirection())
				.andReturn();
		verify(projectDAO,times(1)).save(any(Registration.class));
	}

	@Test
	public void testSaveThrow() throws Exception{
		when(user.getName()).thenReturn("New");
		when(user.getEmailid()).thenReturn("demo@gotgel.org");
		when(user.getPassword()).thenReturn("Example");
		when(user.getMobileno()).thenReturn("1234567890");
		when(file.getInputStream()).thenThrow(new FileNotFoundException("Message"));
		when(file.getName()).thenReturn("profileImage");
		
			MvcResult result =mockMvc.perform(multipart("/saveUser")
					.file(file)
					.param("name", user.getName())
					.param("password", user.getPassword())
					.param("emailid",user.getEmailid())
					.param("mobileno",user.getMobileno())
					.contentType(MediaType.MULTIPART_FORM_DATA)
					)
					.andExpect(status().is3xxRedirection())
					.andReturn();
			System.out.println(result.getResponse().getContentAsString());

	}

	@Test
	public void testLoginPage() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("Login"));
	}

	@Test
	public void testForgotPasswordPage() throws Exception {
		mockMvc.perform(get("/forgotpassword")).andExpect(status().isOk()).andExpect(view().name("ForgotPass"));
	}

	@Test
	public void testReset() throws Exception {
		mockMvc.perform(get("/resetpassword")).andExpect(status().isOk()).andExpect(view().name("ResetPassword"));
	}

	@Test
	public void testSendOTP() throws Exception {
		mockMvc.perform(get("/sendOTP").session(httpSession).param("emailid", "test"))
				.andExpect(status().is3xxRedirection());
	}

	// Verifying OTP while user is NULL
	@Test
	public void testVerifyOTP() throws Exception {
		mockMvc.perform(post("/verify").param("emailid", "test").param("otp", "12345").session(httpSession))
				.andExpect(status().is3xxRedirection());
	}

	// Verifying OTP while user is not NULL and OTP is NULL
	@Test
	public void testVerifyOTPuser() throws Exception {
		Registration newUser = new Registration();
		// when(user).thenReturn(new Registration());
		when(projectDAO.findByEmailid("test")).thenReturn(newUser);
		mockMvc.perform(post("/verify").param("emailid", "test").param("otp", "12345").session(httpSession))
				.andExpect(status().is3xxRedirection());
	}

	// Verifying OTP while user is not NULL and OTP is Incorrect
	@Test
	public void testVerifyOTPuserWithOTP() throws Exception {
		Registration newUser = new Registration();
		newUser.setOTP("1234");
		// when(user).thenReturn(new Registration());
		when(projectDAO.findByEmailid("test")).thenReturn(newUser);
		mockMvc.perform(post("/verify").param("emailid", "test").param("otp", "12345").session(httpSession))
				.andExpect(status().is3xxRedirection());
	}

	// Verifying OTP while user is not NULL and OTP is Correct
	@Test
	public void testVerifyOTPuserWithOTPisEqual() throws Exception {
		Registration newUser = new Registration();
		newUser.setOTP("12345");
		when(projectDAO.findByEmailid("test")).thenReturn(newUser);
		mockMvc.perform(post("/verify").param("emailid", "test").param("otp", "12345").session(httpSession))
				.andExpect(status().is3xxRedirection());
	}
	
	//Checking Change Password and OTP is Incorrect
	@Test
	public void testChangePassword() throws Exception {
		Registration newUser = new Registration();
		newUser.setOTP("1234");
		// when(user).thenReturn(new Registration());
		when(httpSession.getAttribute("emailid")).thenReturn("test");
		when(projectDAO.findByEmailid("test")).thenReturn(newUser);
		mockMvc.perform(post("/change-password").param("password", "testpassword").session(httpSession))
				.andExpect(status().is3xxRedirection());
	}
	
	//Checking Change Password and OTP is Incorrect
		@Test
		public void testChangePasswordwithOTP() throws Exception {
			Registration newUser = new Registration();
			newUser.setOTP("1234");
			// when(user).thenReturn(new Registration());
			when(httpSession.getAttribute("emailid")).thenReturn("test");
			when(httpSession.getAttribute("enteredOTP")).thenReturn("1234");
			when(projectDAO.findByEmailid("test")).thenReturn(newUser);
			mockMvc.perform(post("/change-password").param("password", "testpassword").session(httpSession))
					.andExpect(status().is3xxRedirection());
		}
		
		
}
