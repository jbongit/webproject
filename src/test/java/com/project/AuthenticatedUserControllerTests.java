package com.project;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.ApplicationContextTestUtils;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.mysql.cj.util.TestUtils;
import com.project.config.CustomUserDetails;
import com.project.controller.ProjectController;
import com.project.dao.ProjectDAO;
import com.project.model.Registration;

@SpringBootTest
@AutoConfigureMockMvc

public class AuthenticatedUserControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	//@MockBean
	//private UserDetailsService userDetailsService;
	
	@MockBean
	ProjectDAO projectDAO;
	
	@Mock
	Registration ruser;
	
	@Mock
	MockMultipartFile file;
	
	@Retention(RetentionPolicy.RUNTIME)
	
	@WithSecurityContext(factory = MockUserSecurityContextExample.class)
	public @interface WithMockCustomUser{
		int id() default 0;
		String username() default "";
		String password() default "";
		String roles() default "";
	}
	
//	@BeforeEach
//	public void setup() {
//		MockitoAnnotations.initMocks(this);
//	}
	
//	@BeforeEach
//	public void New(){
//		Registration mockRegistration=new Registration();
//		mockRegistration.setId(2);
//		mockRegistration.setName("Test");
//		mockRegistration.setEmailid("test@user.com");
//		mockRegistration.setPassword(passwordEncoder.encode("user"));
//		mockRegistration.setRole("ROLE_USER");
//		projectDAO.save(mockRegistration);
//	}
	
//	@MockBean
//	private CustomUserDetails customUserDetails;
	
	//@WithSecurityContext(userDetailsBeanName="userDetailsService")
	
//	@WithMockUser(username="user@user.com",password="user",authorities = {"ROLE_USER"})
//	public void testUserDashBoard() throws Exception{
//		Registration newuser=;
//		newuser.setName("User");
//		newuser.setEmailid("testuser");
//		newuser.setId(1);
//		
//		final UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(newuser, newuser);
//		
//		when(authentication.getPrincipal()).thenReturn(newuser);
//		when(securityContext.getAuthentication()).thenReturn(authentication);
//		SecurityContextHolder.setContext(securityContext);
//		when(projectDAO.findById(1)).thenReturn(Optional.of(newuser));
//	mockMvc.perform(get("/user/1"))
//	.andExpect(status().isOk()).andExpect(view().name("UserDashboard"));
//	}
	
	@Test
	//@WithUserDetails(value="example@user.com",userDetailsServiceBeanName = "userDetailsService")
	//@WithSecurityContext(setupBefore = TestExecutionEvent.TEST_EXECUTION)
	//@WithMockUser(username="admin@admin.com",authorities = {"ROLE_ADMIN"},principal="1")
	@WithMockCustomUser(id=1,roles="ROLE_USER",username="user",password = "user")
	public void testUserDashBoard() throws Exception{
//		UserDetails principal= User.withUsername("user@user.com").password("user").authorities("ROLE_USER").build();
//		Authentication authentication=new UsernamePasswordAuthenticationToken(principal, "user",principal.getAuthorities());
//		SecurityContext context=SecurityContextHolder.createEmptyContext();
//		securityContext.setAuthentication(authentication);
//		SecurityContextHolder.setContext(context);
		
		//when(projectDAO.findById(1)).thenReturn(Optional.of(mockRegistration));
//		mockMvc.perform(post("/loginCheck").param("username","user@user.com").param("password", "user")).andExpect(status().is3xxRedirection());
		mockMvc.perform(get("/user/1")).andExpect(status().isOk()).andExpect(view().name("UserDashboard"));
		//mockMvc.perform(formLogin("/login").user("user@user.com").password("user"));
	}
	
	@Test
	//@WithUserDetails(value="debnmmo@nmusennr.comlatest")
	@WithMockCustomUser(id=1,roles="ROLE_USER",username="user",password = "user")
	public void testDeleteUserDashBoardProfile() throws Exception{
	//doNothing().when(projectDAO).delete(any(Registration.class));
	mockMvc.perform(get("/user/1/delete")).andExpect(status().is3xxRedirection());
	}
	
	@Test
	@WithMockCustomUser(id=1,roles="ROLE_USER",username="user",password = "user")
	public void testUpdateUserDashBoardProfile() throws Exception{
		mockMvc.perform(get("/user/1/update")).andExpect(status().isOk()).andExpect(view().name("UpdateDetails"));
	}
	
	//Checking Change Password Page
	@Test
	@WithMockCustomUser(id=1,roles="ROLE_USER",username="user",password = "user")
	public void testChangeOldPassword() throws Exception{
		mockMvc.perform(get("/user/1/changepassword")).andExpect(status().isOk()).andExpect(view().name("ChangePassword"));
	}
	
	//Checking Change Old Password Successfully
	@Test
	public void testChangeOldPasswordSuccessfully() throws Exception{
		Registration newUser=new Registration();
		newUser.setId(1);
		newUser.setPassword("$2a$10$3ZXHiyvC9Y4F4Q6Lnx1Em.rU2ZHYz7lnEH1P3OQWHYxIyjY1rZzqO");
		when(projectDAO.findById(1)).thenReturn(Optional.of(newUser));
		mockMvc.perform(post("/change-old-password").param("id","1").param("old-password","$2a$10$3ZXHiyvC9Y4F4Q6Lnx1Em.rU2ZHYz7lnEH1P3OQWHYxIyjY1rZzqO").param("new-password","testNEW").param("confirm-password","testCONFIRM")).andExpect(status().is3xxRedirection());
	}
	
	@Test
	@WithMockCustomUser(id=1,roles="ROLE_USER",username="user",password = "user")
	public void testUpdatePerson() throws Exception {
		when(ruser.getName()).thenReturn("user");
		when(ruser.getEmailid()).thenReturn("user@example.com");
		when(ruser.getPassword()).thenReturn("user");
		when(ruser.getMobileno()).thenReturn("1234567890");
		when(projectDAO.findByEmailid(ruser.getEmailid())).thenReturn(ruser);
		when(file.getName()).thenReturn("profileImage");
		//doNothing().when(mailSender).send(any(SimpleMailMessage.class));;
		//		doNothing().when(projectDAO.save(any(Registration.class)));
		//FileInputStream fis = new FileInputStream(
				//"C:/Users/jalaj.bhandula/OneDrive - HCL Technologies Ltd/Pictures/Screenshots/Screenshot (50).png");
		//MockMultipartFile multipartFile = new MockMultipartFile("profileImage", file);
		MvcResult result =mockMvc.perform(multipart("/updateUser")
				.file(file)
				.param("name", ruser.getName())
				.param("password", ruser.getPassword())
				.param("emailid",ruser.getEmailid())
				.param("mobileno",ruser.getMobileno())
				.param("id","1")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				)
				.andExpect(status().is3xxRedirection())
				.andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
		
	}
	
	@Test
	@WithMockCustomUser(id=1,roles="ROLE_USER",username="user",password = "user")
	public void testUpdatePersonhasErrors() throws Exception {
		when(ruser.getName()).thenReturn("user");
		when(ruser.getEmailid()).thenReturn("user");
		when(ruser.getPassword()).thenReturn("user");
		when(ruser.getMobileno()).thenReturn("1234567890");
		when(projectDAO.findByEmailid(ruser.getEmailid())).thenReturn(ruser);
		when(file.getName()).thenReturn("profileImage");
		//doNothing().when(mailSender).send(any(SimpleMailMessage.class));;
		//		doNothing().when(projectDAO.save(any(Registration.class)));
		//FileInputStream fis = new FileInputStream(
				//"C:/Users/jalaj.bhandula/OneDrive - HCL Technologies Ltd/Pictures/Screenshots/Screenshot (50).png");
		//MockMultipartFile multipartFile = new MockMultipartFile("profileImage", file);
		MvcResult result =mockMvc.perform(multipart("/updateUser")
				.file(file)
				.param("name", ruser.getName())
				.param("password", ruser.getPassword())
				.param("emailid",ruser.getEmailid())
				.param("mobileno",ruser.getMobileno())
				.param("id","1")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				)
				.andExpect(status().is3xxRedirection())
				.andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
		
	}
	
	
//	@Test
//	//@WithMockUser(username="user@user.com",password="user",authorities = {"ROLE_USER"})
//	@WithUserDetails(value="admin@admin.com")
//	public void testUserDashBoard() throws Exception{
////		newuser.setPassword("user");
////		when(projectDAO.findById(1)).thenReturn(newuser);
////		when(auth.getPrincipal()).thenReturn(newuser);
//		
////		UserDetails checkuser= User.withUsername("usr@user.com").password("user").authorities("ROLE_USER").build();
////		Authentication authentication=new UsernamePasswordAuthenticationToken(checkuser, null);
////		System.out.println(authentication.getPrincipal());
////		Registration newuser=new Registration();
////		newuser.setId(1);
////		newuser.setEmailid("user@user.com");
////		newuser.setRole("ROLE_UER");
////		when(projectDAO.findById(1)).thenReturn(Optional.of(newuser));
//		//when(user.getId()).thenReturn(1);
//		//final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(checkuser, null,checkuser.getAuthorities() );
//	    //SecurityContextHolder.getContext().setAuthentication(authentication);
//		//mockMvc.perform(get("/user/"+checkuser.g).with(SecurityMockMvcRequestPostProcessors.authentication(authentication))).andExpect(view().name("UserDashBoard"));
//	//CustomUserDetails cuser=projectDAO.findByid
//	mockMvc.perform(get("/user/dashboard")).andExpect(status().isOk()).andExpect(view().name("UserDashboard"));
//	}
	static class MockUserSecurityContextExample implements WithSecurityContextFactory<WithMockCustomUser>{

		@Override
		public SecurityContext createSecurityContext(WithMockCustomUser withUser) {
			Registration mockRegistration=new Registration();
			mockRegistration.setId(withUser.id());
			mockRegistration.setEmailid(withUser.username());
			mockRegistration.setRole(withUser.roles());
			SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
			CustomUserDetails principal=new CustomUserDetails(mockRegistration);
			Authentication auth=new UsernamePasswordAuthenticationToken(principal,withUser.password(),principal.getAuthorities());
			securityContext.setAuthentication(auth);
			return securityContext;
			
		}
		
	}
}


