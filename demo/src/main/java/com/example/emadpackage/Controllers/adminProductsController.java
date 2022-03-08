package com.example.emadpackage.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.emadpackage.models.data.category;
import com.example.emadpackage.models.data.categoryRepository;
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
	private String index(Model model, @RequestParam(value="page",required = false) Integer p) {
		
		int perPage = 6;
		int page = (p!=null)? p : 0;		
		
		
		Pageable pageable = PageRequest.of(page,perPage);
		
		Page<product> products = productRepo.findAll(pageable);
		List<category> categories = categoryRepo.findAll();

		HashMap<Integer, String> cats = new HashMap<>(); // here it is 'cats'

		for (category cat : categories) { // here it is 'cat'
			cats.put(cat.getId(), cat.getName());
		}

		model.addAttribute("products", products);
		model.addAttribute("cats", cats);
		
		long count = productRepo.count();
		double pageCount = Math.ceil((double)count/(double)perPage);
		
		model.addAttribute("pageCount", (int)pageCount);
		model.addAttribute("perPage", perPage);
		model.addAttribute("count", count);
		model.addAttribute("page", page);

		return "admin/products/index";
	}

	@GetMapping("/add")
	private String add(product product, Model model) {
		List<category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		return "admin/products/add";
	}

	// Below binding result should always be next to validation , so spring should
	// know what validation message to show if input is wrong
	@PostMapping("/add")
	public String add(@Valid product product, BindingResult bindResult, MultipartFile file,
			RedirectAttributes redirectAttributes, Model model) throws IOException {

		List<category> categories = categoryRepo.findAll();

		if (bindResult.hasErrors()) {
			model.addAttribute("categories", categories);
			return "admin/products/add";
		}

		boolean fileOK = false;
		byte[] bytes = file.getBytes();
		String fileName = file.getOriginalFilename();
		Path path = Paths.get("src/main/resources/static/media/" + fileName);

		if (fileName.endsWith("jpg") || fileName.endsWith("png")) {
			fileOK = true;
		}

		redirectAttributes.addFlashAttribute("message", "Product added");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		String slug = product.getName().toLowerCase().replace(" ", "-");

		product productExists = productRepo.findBySlug(slug);

		if (!fileOK) {
			redirectAttributes.addFlashAttribute("message", "Image must be jpg or png");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("product", "product");
		}

		else if (productExists != null) {
			redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("product", "product");

		} else {
			product.setSlug(slug);
			product.setImage(fileName);
			productRepo.save(product);
			Files.write(path, bytes);

		}

		return "redirect:/admin/products/add";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {

		// page page = pageRepo.getOne(id); //deprecated

		product product = productRepo.getById(id);
		List<category> categories = categoryRepo.findAll(); // List will come below product, because models are set that
															// way only.

		model.addAttribute("product", product);
		model.addAttribute("categories", categories);

		return "admin/products/edit";

	}

	@PostMapping("/edit")
	public String edit(@Valid product product, BindingResult bindResult, MultipartFile file,
			RedirectAttributes redirectAttributes, Model model) throws IOException {

		product currentProduct = productRepo.getById(product.getId());

		List<category> categories = categoryRepo.findAll();

		if (bindResult.hasErrors()) {
			model.addAttribute("productName", currentProduct.getName());
			model.addAttribute("categories", categories);
			return "admin/products/edit";
		}

		boolean fileOK = false;
		byte[] bytes = file.getBytes();
		String fileName = file.getOriginalFilename();
		Path path = Paths.get("src/main/resources/static/media/" + fileName);

		if (!file.isEmpty()) {
			if (fileName.endsWith("jpg") || fileName.endsWith("png")) {
				fileOK = true;
			}
		} else {
			fileOK = true;
		}

		redirectAttributes.addFlashAttribute("message", "Product edited");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		String slug = product.getName().toLowerCase().replace(" ", "-");

		product productExists = productRepo.findBySlugAndIdNot(slug, product.getId());

		if (!fileOK) {
			redirectAttributes.addFlashAttribute("message", "Image must be jpg or png");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("product", "product");
		}

		else if (productExists != null) {
			redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("product", "product");

		} else {
			product.setSlug(slug);

			if (!file.isEmpty()) {
				Path path2 = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
				Files.delete(path2);
				product.setImage(fileName);
				Files.write(path, bytes);
			} else {
				product.setImage(currentProduct.getImage());
			}

			productRepo.save(product);

		}

		return "redirect:/admin/products/edit/" + product.getId();
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) throws IOException {
		
		product product = productRepo.getById(id);
		product currentProduct = productRepo.getById(product.getId());
		
		Path path2 = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
		Files.delete(path2);
		productRepo.deleteById(id);
		
		redirectAttributes.addFlashAttribute("message", "Product deleted");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		return "redirect:/admin/products";

	}
}
