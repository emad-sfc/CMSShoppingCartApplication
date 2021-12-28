package com.example.emadpackage.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.emadpackage.models.data.page;
import com.example.emadpackage.models.data.pageRepository;

@Controller
@RequestMapping("/admin/pages")
public class adminPagesController {

	@Autowired
	private pageRepository pageRepo;

	// Below constructor not needed because we are using @Autowirded annotation.
//	public adminPagesController(pageRepository pageRepo) {
//		this.pageRepo = pageRepo;
//	}

	@GetMapping
	public String index(Model model) {

		List<page> pages = pageRepo.findAll();
		model.addAttribute("pages", pages);
		return "admin/pages/index";
	}

	@GetMapping("/add")
	public String add(@ModelAttribute page page) { // Here we are passing object of our page entity

		// model.addAttribute("page", new page()); //This method will also work, but we
													// need to pass
		return "admin/pages/add"; 					// Model object in paramaters
	}

	@PostMapping("/add")
	public String add(@Valid page page, BindingResult bindResult, RedirectAttributes redirectAttributes, Model model) {

		if (bindResult.hasErrors()) {
			return "admin/pages/add";
		}

		redirectAttributes.addFlashAttribute("message", "Page added");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-")
				: page.getSlug().toLowerCase().replace(" ", "-");

		page slugExists = pageRepo.findBySlug(slug);

		if (slugExists != null) {
			redirectAttributes.addFlashAttribute("message", "Slug exists, choose another");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("page", page);
		} else {
			page.setSlug(slug);
			page.setSorting(100);
			pageRepo.save(page); // Note here class name and object name
		} // both are starting with page. Don't get confused.

		return "redirect:/admin/pages/add";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {

		// page page = pageRepo.getOne(id); //deprecated

		page page = pageRepo.getById(id);

		model.addAttribute("page", page);

		return "admin/pages/edit";

	}
	
	@PostMapping("/edit")
	public String edit(@Valid page page, BindingResult bindResult, RedirectAttributes redirectAttributes, Model model) {
		
		page pageCurrent = pageRepo.getById(page.getId());
		
		if (bindResult.hasErrors()) {
			model.addAttribute("pageTitle", pageCurrent.getTitle());
			return "admin/pages/edit";
		}

		redirectAttributes.addFlashAttribute("message", "Page added");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-")
				: page.getSlug().toLowerCase().replace(" ", "-");

//		page slugExists = pageRepo.findBySlug(page.getId(),slug);
		
		page slugExists = pageRepo.findBySlugAndIdNot(slug, page.getId());
		

		if (slugExists != null) {
			redirectAttributes.addFlashAttribute("message", "Slug exists, choose another");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("page", page);
		} else {
			page.setSlug(slug);
			
			pageRepo.save(page); // Note here class name and object name
		} 						 // both are starting with page. Don't get confused.

		return "redirect:/admin/pages/edit/" + page.getId();
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
		
		pageRepo.deleteById(id);
		
		redirectAttributes.addFlashAttribute("message", "Page deleted");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		return "redirect:/admin/pages";

	}

}
