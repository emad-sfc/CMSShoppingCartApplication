package com.example.emadpackage.Security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.emadpackage.models.data.user;
import com.example.emadpackage.models.data.userRepository;

@Controller
@RequestMapping("/register")
public class registrationController {
	
	@Autowired
	private userRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping
	public String register(user user) {
		return "register";
		
	}
	
	@PostMapping
	public String register(@Valid user user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "register";
		}
		
		if(! user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("passwordMatchProblem", "Passwords do not match!");
			return "register";
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		
		return "redirect:/login";
	}
}
