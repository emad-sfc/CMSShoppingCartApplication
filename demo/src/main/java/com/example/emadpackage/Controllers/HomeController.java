package com.example.emadpackage.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/someRandomPage")
	public String home() {
		return "home";
	}

}
