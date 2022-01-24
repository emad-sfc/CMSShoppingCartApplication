package com.example.emadpackage.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.emadpackage.models.data.page;
import com.example.emadpackage.models.data.pageRepository;

@Controller
@RequestMapping("/")
public class PagesController {
	
	@Autowired
	pageRepository pageRepo;
	
	@GetMapping
	public String home(Model model) {
		page page = pageRepo.findBySlug("home");
		model.addAttribute("page", page);
		return "page";
	}

}
