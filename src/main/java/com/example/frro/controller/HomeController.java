package com.example.frro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Frro")
public class HomeController {
	@GetMapping("/visitor")
	public String getIndex() {
		return "Index";
	}
	
	  @GetMapping("/visitorProfile")
	    public String getVisitorProfile() {
	        return "redirect:/visitor";  // Redirects to the visitor page
	    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "adminlogin";
    }

}
