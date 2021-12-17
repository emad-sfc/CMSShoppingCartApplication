package com.example.emadpackage.Controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	@GetMapping("/add")
	public String add(@ModelAttribute page page) {    //Here we are passing object of our page entity
//		model.addAttribute("page", new page());      //This method will also work, but we need to pass
		return "admin/pages/add";						//Model object in paramaters
	}
	
	
	
	
	
}
