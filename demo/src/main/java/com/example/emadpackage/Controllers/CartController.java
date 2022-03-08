package com.example.emadpackage.Controllers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.emadpackage.models.data.cart;
import com.example.emadpackage.models.data.product;
import com.example.emadpackage.models.data.productRepository;

@Controller
@RequestMapping("/cart")
@SuppressWarnings("unchecked")
public class CartController {

	@Autowired
	private productRepository productRepo;

	@GetMapping("/add/{id}")
	public String add(@PathVariable int id, 
					  HttpSession session, 
					  Model model,
					  @RequestParam(value = "cartPage",required=false)String cartPage) {

		product product = productRepo.getById(id);

		if (session.getAttribute("cart") == null) {

			HashMap<Integer, cart> cart = new HashMap<>();
			cart.put(id, new cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
			session.setAttribute("cart", cart);

		} else {

			HashMap<Integer, cart> cart = (HashMap<Integer, cart>) session.getAttribute("cart");

			if (cart.containsKey(id)) {
				int qty = cart.get(id).getQuantity();
				cart.put(id, new cart(id, product.getName(), product.getPrice(), ++qty, product.getImage()));

			} else {
				cart.put(id, new cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
				session.setAttribute("cart", cart);
			}
		}

		HashMap<Integer, cart> cart = (HashMap<Integer, cart>) session.getAttribute("cart");
		int size = 0;
		double total = 0;

		for (cart value : cart.values()) {
			size += value.getQuantity();
			total += value.getQuantity() * Double.parseDouble(value.getPrice());
		}
	
		model.addAttribute("size", size);
		model.addAttribute("total", total);
		
		if(cartPage != null) {
			return "redirect:/cart/view";
		}

		return "cart_view";

	}
	
	@GetMapping("/subtract/{id}")
	public String subtract(@PathVariable int id, 
					  HttpSession session, 
					  Model model, HttpServletRequest httpServletRequest){
		product product = productRepo.getById(id);
		
		HashMap<Integer, cart> cart = (HashMap<Integer, cart>) session.getAttribute("cart");
		
		int qty = cart.get(id).getQuantity();
		
		if (qty==1) {
			cart.remove(id);
			if(cart.size() == 0) {
				session.removeAttribute("cart");
			}
		}else {
			cart.put(id, new cart(id, product.getName(), product.getPrice(), --qty, product.getImage()));   //using put method but --qty.
		}
		
		String refererLink = httpServletRequest.getHeader("referer");   //referer is a keyword    https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Referer
		return "redirect:" + refererLink;
		
	}
	
	@GetMapping("/remove/{id}")
	public String remove(@PathVariable int id, 
					  HttpSession session,
					 HttpServletRequest httpServletRequest){		
		
		HashMap<Integer, cart> cart = (HashMap<Integer, cart>) session.getAttribute("cart");
		
		cart.remove(id);
		
		if(cart.size()==0) {
			session.removeAttribute("cart");
		}
		
		String refererLink = httpServletRequest.getHeader("referer");   //referer is a keyword    https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Referer
		return "redirect:" + refererLink;
		
	}
	
	@GetMapping("/clear")
	public String clear(HttpSession session,
					 HttpServletRequest httpServletRequest){		
		
		HashMap<Integer, cart> cart = (HashMap<Integer, cart>) session.getAttribute("cart");	
		
		session.removeAttribute("cart");
		
		String refererLink = httpServletRequest.getHeader("referer");   //referer is a keyword    https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Referer
		return "redirect:" + refererLink;
		
	}
	
	@RequestMapping("/view")
	public String view(HttpSession session, Model model) {
		
		if(session.getAttribute("cart")==null)
			return "redirect:/";
		
		HashMap<Integer, cart> cart = (HashMap<Integer, cart>)session.getAttribute("cart");
		model.addAttribute("cart", cart);
		model.addAttribute("notCartViewPage", true);
		
		return "cart";
	}

}
