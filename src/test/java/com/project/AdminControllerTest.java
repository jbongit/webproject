package com.project;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.project.AuthenticatedUserControllerTests.WithMockCustomUser;
import com.project.dao.ApproverDAO;
import com.project.dao.ProjectDAO;
import com.project.model.Registration;


@WithMockCustomUser(id=1,roles="ROLE_ADMIN",username="admin",password = "admin")
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ProjectDAO projectDAO;
	
	@MockBean
	ApproverDAO approverDAO;
	
	@Test
	public void testAdminDashBoard() throws Exception{
		mockMvc.perform(get("/admin/dashboard")).andExpect(status().isOk()).andExpect(view().name("AdminDashboard"));
	}
	
	@Test
	public void testGenerateReport() throws Exception{
		List<Registration> list=new ArrayList<>();
		list.add(new Registration());
		list.get(0).setRegistrationdateTime(LocalDateTime.now());
		list.add(new Registration());
		list.get(1).setRegistrationdateTime(LocalDateTime.now());
		when(projectDAO.findAll()).thenReturn(list);
		mockMvc.perform(get("/admin/report")).andExpect(status().isOk());
	}
	
	@Test
	public void testAdminUserDashBoard() throws Exception{
		mockMvc.perform(get("/admin/user/1")).andExpect(status().isOk()).andExpect(view().name("UserApprovalProfile"));
	}
	
	@Test
	public void testPendingApprovals() throws Exception{
		mockMvc.perform(get("/admin/pending-approvals")).andExpect(status().isOk()).andExpect(view().name("PendingApprovals"));
	}
	
	@Test
	public void testApproveUser() throws Exception{
		mockMvc.perform(get("/admin/user/1/approved")).andExpect(status().is3xxRedirection());
	}
	
	@Test
	public void testFunction() throws Exception{
		mockMvc.perform(get("/admin/user/1/rejected")).andExpect(status().is3xxRedirection());
	}
	
	@Test
	public void testSearch() throws Exception{
		mockMvc.perform(post("/admin/search").param("searchQuery","testSearch")).andExpect(status().isOk()).andExpect(view().name("AdminDashboard"));
	}
}
