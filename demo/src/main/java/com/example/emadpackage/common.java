package com.example.emadpackage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.emadpackage.models.data.cart;
import com.example.emadpackage.models.data.category;
import com.example.emadpackage.models.data.categoryRepository;
import com.example.emadpackage.models.data.page;
import com.example.emadpackage.models.data.pageRepository;

@ControllerAdvice
@SuppressWarnings("unchecked")
public class common {
	
	@Autowired
	pageRepository pageRepo;
	
	@Autowired
	categoryRepository categoryRepo;
	
	@ModelAttribute
	public void sharedData(Model model, HttpSession session) {      //in this method return type is void! Therefore at the end of this method we are not returning any value.
		List<page> pages = pageRepo.findAllByOrderBySortingAsc();
		
		List<category> categories = categoryRepo.findAll();
		
		boolean cartActive = false;
		
		if(session.getAttribute("cart")!=null) {
			HashMap<Integer, cart> cart = (HashMap<Integer, cart>)session.getAttribute("cart");
			int size = 0;
			double total = 0;
			
			for (cart value : cart.values()) {
				size +=  value.getQuantity();
				total += value.getQuantity()  * Double.parseDouble(value.getPrice());				
			}
			
			model.addAttribute("csize", size);
			model.addAttribute("ctotal", total);
			cartActive = true;
		}
		
		model.addAttribute("cpages", pages);
		model.addAttribute("ccategories", categories);
		model.addAttribute("cartActive", cartActive);
	}

}
