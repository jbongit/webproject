package com.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.dao.ProjectDAO;
import com.project.model.Registration;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private ProjectDAO projectDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Registration user=projectDAO.findByEmailid(username);
		if(user==null) {
			throw new UsernameNotFoundException("Could Not Found User");
		}
		
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		return customUserDetails;
	}

}
