package com.example.emadpackage.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.emadpackage.models.data.category;
import com.example.emadpackage.models.data.categoryRepository;

@Controller
@RequestMapping("/admin/categories")
public class adminCategoriesController {

	@Autowired
	private categoryRepository categoryRepo;

	@GetMapping
	public String index(Model model) {

		List<category> categories = categoryRepo.findAllByOrderBySortingAsc();
		model.addAttribute("categories", categories);

		return "admin/categories/index";
	}

	// Below approach can also be used using Model attribute for category. This way,
	// this model attribute can be used
	// by all the methods of the class.

//	@ModelAttribute("category")						//This way this controller method can be shared
//	public category getCategory() {					//by multiple views
//		return new category();
//	}
//	
//	@GetMapping("/add")
//	public String add(){
//		return "admin/categories/add";
//	}

	@GetMapping("/add")
	public String add(category category) {
		return "admin/categories/add";
	}

	@PostMapping("/add")
	public String add(@Valid category category, BindingResult bindResult, RedirectAttributes redirectAttributes,
			Model model) {

		if (bindResult.hasErrors()) {
			return "admin/categories/add";
		}

		redirectAttributes.addFlashAttribute("message", "Category added");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		String slug = category.getName().toLowerCase().replace(" ", "-");
		category categoryExists = categoryRepo.findByName(category.getName());

		if (categoryExists != null) {
			redirectAttributes.addFlashAttribute("message", "Category exists, choose another");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("categoryInfo", category);
		} else {
			category.setSlug(slug);
			category.setSorting(100);
			categoryRepo.save(category); // Note here class name and object name
		} // both are starting with page. Don't get confused.

		return "redirect:/admin/categories/add";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {

		// page page = pageRepo.getOne(id); //deprecated

		category category = categoryRepo.getById(id);

		model.addAttribute("category", category);

		return "admin/categories/edit";

	}

	@PostMapping("/edit")
	public String edit(@Valid category category, BindingResult bindResult, RedirectAttributes redirectAttributes,
			Model model) {

		category categoryCurrent = categoryRepo.getById(category.getId());

		if (bindResult.hasErrors()) {
			model.addAttribute("categoryName", categoryCurrent.getName());
			return "admin/categories/edit";
		}

		redirectAttributes.addFlashAttribute("message", "Category edited");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		String slug = category.getName().toLowerCase().replace(" ", "-");
		category categoryExists = categoryRepo.findByName(category.getName());

		if (categoryExists != null) {
			redirectAttributes.addFlashAttribute("message", "Category exists, choose another");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

		} else {
			category.setSlug(slug);

			categoryRepo.save(category);
		}

		return "redirect:/admin/categories/edit/" + category.getId();
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {

		categoryRepo.deleteById(id);

		redirectAttributes.addFlashAttribute("message", "Category deleted");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		return "redirect:/admin/categories";

	}
	
	@PostMapping("/reorder")
	public @ResponseBody String reorder(@RequestParam("id[]") int[] id) {
		
		int count = 1;
		category category;
		
		for (int categoryId : id) {
			category = categoryRepo.getById(categoryId);
			category.setSorting(count);
			categoryRepo.save(category);
			count++;
		}
		
		return "ok";
		
		
	}

}
