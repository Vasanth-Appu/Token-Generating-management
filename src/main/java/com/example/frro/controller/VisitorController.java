package com.example.frro.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.frro.entity.VisitorEntry;
import com.example.frro.service.VisitorService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Controller
@RequestMapping("/Frro")
public class VisitorController {

    @Autowired
    private VisitorService visitorservice;

    

	@GetMapping("/visitorprofile")
	public String getIndex(Model model) {
		model.addAttribute("country", visitorservice.getCountries());
		model.addAttribute("code", visitorservice.getCountrycode());
		//model.addAttribute("visitorDesc", visitorservice.getVisitorPurpose());
		model.addAttribute("visitorEntry", new VisitorEntry());

		return "index1";
	}
	@PostMapping("/savedata")
	public String saveVisitorData(
	      @ModelAttribute("visitorEntry") VisitorEntry visitorEntry,
	      BindingResult bindingResult,
	      @RequestParam(value = "webcamDataUrl", required = false) String webcamDataUrl,
	      Model model, RedirectAttributes redirectAttributes) throws IOException {

	    if (bindingResult.hasErrors()) {
	        model.addAttribute("message", "Validation errors occurred");
	        model.addAttribute("messageType", "error");
	        return "index1"; // Render the same view with errors
	    }

	    if (webcamDataUrl != null && !webcamDataUrl.isEmpty()) {
	        String[] img = webcamDataUrl.split(",");
	        if (img.length > 1) {
	            String imageData = img[1];
	            byte[] photoBytes = Base64.getDecoder().decode(imageData);
	            visitorEntry.setVisitor_img(photoBytes);
	        }
	    }

	    try {
	        Map<String, String> result = visitorservice.saveVisitor(visitorEntry);
	        String dailyToken = result.get("dailyToken");    
	        String currentDate = result.get("currentDate");
	        
	        //preview info
	        String visitorname= visitorEntry.getApplicant_full_name();
	        String address=visitorEntry.getVisitor_address();
	        String passport=visitorEntry.getApplicant_ppt_no();
	        String localno = visitorEntry.getVisitor_contact_no_india();
	        String nationality=visitorEntry.getApplicant_nationality();
	        String idtype=visitorEntry.getVisitor_id_type() ;
	        String applicantname=visitorEntry.getApplicant_full_name();
	        byte[] visitorImage = visitorEntry.getVisitor_img();
	       // String time=visitorEntry.getFormattedTimestamp();
	        String purpose = visitorEntry.getVisit_purpose_other_desc();

	     
	        // Convert image to Base64 string
	     String base64Image = visitorImage != null ? Base64.getEncoder().encodeToString(visitorImage) : "NA";


	        String message = (webcamDataUrl != null && !webcamDataUrl.isEmpty())
	            ? "Hi!!  "+visitorname+" Your Details Saved . please Note Your Token NO"
	            : "Hey!!  "+visitorname+"   No photo uploaded.OtherWise All Saved , please Note Your Token NO ";
// Add a flag to control modal display

	        // local directory to save PDF
	        String localDirectory = "f:/generated_pdfs/";
	        String pdfFilename = "Token_" + dailyToken + "_" + UUID.randomUUID().toString() + ".pdf";
	        Path pdfPath = Paths.get(localDirectory, pdfFilename);

	        // Create directories if they don't exist
	        Files.createDirectories(pdfPath.getParent());

	        // Generate PDF and save it locally
	        try (PdfWriter writer = new PdfWriter(Files.newOutputStream(pdfPath));
	             PdfDocument pdf = new PdfDocument(writer);
	             Document document = new Document(pdf)) {

	            document.add(new Paragraph("Visitor Token Information"));
	            document.add(new Paragraph(message));
	            document.add(new Paragraph("Today your Token is : "+ dailyToken));
	            document.add(new Paragraph("For the DATE : "+currentDate));
	            document.add(new Paragraph("Purpose : "+purpose));


	        }

	        // Redirect with message and PDF file path for local access
	        redirectAttributes.addFlashAttribute("message", message);
	        redirectAttributes.addFlashAttribute("passport", passport);
	        redirectAttributes.addFlashAttribute("address", address);
	        redirectAttributes.addFlashAttribute("localno", localno);
	        redirectAttributes.addFlashAttribute("applicantname", applicantname);
	        redirectAttributes.addFlashAttribute("image", base64Image);
	        redirectAttributes.addFlashAttribute("nationality", nationality);
	        redirectAttributes.addFlashAttribute("idtype", idtype != null ? idtype : "NA");
	        redirectAttributes.addFlashAttribute("token", dailyToken);
	        redirectAttributes.addFlashAttribute("date", currentDate);
	        redirectAttributes.addFlashAttribute("purpose", purpose);






	        redirectAttributes.addFlashAttribute("messageType", "success");

	        return "redirect:/Frro/visitorprofile?saved=true";
	    } catch (Exception e) {
	        e.printStackTrace();
	        String errorMessage = "Save Data Failed: " + e.getMessage();
	        redirectAttributes.addFlashAttribute("message", errorMessage);
	        redirectAttributes.addFlashAttribute("messageType", "error");
	        return "redirect:/Frro/visitor?saved=false";
	    }
	}


}
