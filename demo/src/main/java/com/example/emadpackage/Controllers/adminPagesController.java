package com.example.emadpackage.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.emadpackage.models.data.page;
import com.example.emadpackage.models.data.pageRepository;



@Controller
@RequestMapping("/admin/pages")
public class adminPagesController {

	private pageRepository pageRepo;
	
	
	public adminPagesController(pageRepository pageRepo) {
		this.pageRepo = pageRepo;
	}


	@GetMapping
	public String index(Model model) {
		
		List<page> pages = pageRepo.findAll();
		model.addAttribute("pages",pages);
		return "admin/pages/index";
	}

}
