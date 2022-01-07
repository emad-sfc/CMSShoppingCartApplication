package com.example.emadpackage.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.emadpackage.models.data.category;
import com.example.emadpackage.models.data.categoryRepository;
import com.example.emadpackage.models.data.page;
import com.example.emadpackage.models.data.product;
import com.example.emadpackage.models.data.productRepository;

@Controller
@RequestMapping("/admin/products")
public class adminProductsController {

	@Autowired
	private productRepository productRepo;

	@Autowired
	private categoryRepository categoryRepo;

	@GetMapping
	private String index(Model model) {
		List<product> products = productRepo.findAll();
		model.addAttribute("products", products);
		return "admin/products/index";
	}

	@GetMapping("/add")
	private String add(product product, Model model) {
		List<category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		return "admin/products/add";
	}

	@PostMapping("/add")
	public String add(@Valid product product, MultipartFile file, BindingResult bindResult,
			RedirectAttributes redirectAttributes, Model model) throws IOException {

		if (bindResult.hasErrors()) {
			return "admin/categories/add";
		}
		
		boolean fileOK = false;
		byte[] bytes = file.getBytes();
		String fileName = file.getOriginalFilename();
		Path path = Paths.get("src/main/resources/static/media"+fileName);
		
		if(fileName.endsWith("jpg") || fileName.endsWith("png")) {
			fileOK=true;
		}
		

		redirectAttributes.addFlashAttribute("message", "Product added");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		String slug = product.getName().toLowerCase().replace(" ", "-");

		product productExists = productRepo.findBySlug(slug);
		
		if(!fileOK) {
			redirectAttributes.addFlashAttribute("message", "Image must be jpg or png");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}
		
			

		else if(productExists != null) {
			redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			
		} else {
			product.setSlug(slug);
			product.setImage(fileName);
			productRepo.save(product);
			Files.write(path, bytes);
			
		} 

		return "redirect:/admin/products/add";
	}
}
