package com.example.emadpackage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.emadpackage.models.data.category;
import com.example.emadpackage.models.data.categoryRepository;
import com.example.emadpackage.models.data.page;
import com.example.emadpackage.models.data.pageRepository;

@ControllerAdvice
public class common {
	
	@Autowired
	pageRepository pageRepo;
	
	@Autowired
	categoryRepository categoryRepo;
	
	@ModelAttribute
	public void sharedData(Model model) {      //in this method return type is void! Therefore at the end of this method we are not returning any value.
		List<page> pages = pageRepo.findAllByOrderBySortingAsc();
		
		List<category> categories = categoryRepo.findAll();
		
		model.addAttribute("cpages", pages);
		model.addAttribute("ccategories", categories);
	}

}
