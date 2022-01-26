package com.example.emadpackage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.emadpackage.models.data.page;
import com.example.emadpackage.models.data.pageRepository;

@ControllerAdvice
public class common {
	
	@Autowired
	pageRepository pageRepo;
	
	@ModelAttribute
	public void sharedData(Model model) {      //in this method return type is void!
		List<page> pages = pageRepo.findAllByOrderBySortingAsc();
		model.addAttribute("cpages", pages);
	}

}
