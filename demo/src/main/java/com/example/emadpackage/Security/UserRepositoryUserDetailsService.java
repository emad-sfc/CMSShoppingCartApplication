package com.example.emadpackage.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.emadpackage.models.data.admin;
import com.example.emadpackage.models.data.adminRepository;
import com.example.emadpackage.models.data.user;
import com.example.emadpackage.models.data.userRepository;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService{
	
	@Autowired
	private userRepository userRepo;
	
	@Autowired
	private adminRepository adminRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		user user = userRepo.findByUsername(username);
		admin admin = adminRepo.findByUsername(username);
		
		if(user != null)
			return user;					
		
		if(admin != null)
			return admin;
			
		throw new UsernameNotFoundException("User: " + username + "Not found !");
		
	}

}
