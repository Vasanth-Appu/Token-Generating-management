package com.example.frro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.frro.entity.VisitorRemarks;
import com.example.frro.service.RemarksService;

@Controller
public class RemarksController {
	@Autowired RemarksService remarkService;
	
	@GetMapping("/remarksuccess")
    public String getRemarksPage(Model model) {
		model.addAttribute("visitorRemarks", new VisitorRemarks());

    	
		return "VisitorsList" ;
    }
//
//	@GetMapping("/visitors/edit")
//	public String editVisitor(@ModelAttribute("visitorRemarks") VisitorRemarks visitorRemarks, RedirectAttributes redirectAttributes) {
//	    try {
//	        remarkService.saveRemarks(visitorRemarks);
//	        redirectAttributes.addFlashAttribute("message2", "Visitor Remarks updated successfully!");
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        redirectAttributes.addFlashAttribute("message2", "An error occurred while updating the remarks.");
//	        return "redirect:/remarksuccess"; // Ensure you have this template
//	    }
//
//	    return "VisitorsList"; // Correct syntax for redirecting
//	}	
	}

