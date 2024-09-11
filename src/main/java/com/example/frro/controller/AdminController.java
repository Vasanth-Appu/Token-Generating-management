package com.example.frro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.frro.entity.PurposeCount;
import com.example.frro.entity.VisitorEntry;
import com.example.frro.entity.VisitorRemarks;
import com.example.frro.service.AdminService;
import com.example.frro.service.HodService;
import com.example.frro.service.RemarksService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Frro")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private HodService hodService;

    @Autowired
    private RemarksService remarkService;

    @GetMapping("/adminlogin")
    public String adminLogin(Model model) {
        return "adminlogin";
    }

    @PostMapping("/authenticate")
    public String authenticate(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "password", required = false) String password,
            // @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fromDate,
            // @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate,
            HttpSession session,
            Model model) {

        if (email == null || password == null) {
            model.addAttribute("error", "Email and Password are required.");
            return "adminlogin";
        }

//        if (email.equals("admin@gmail.com") && password.equals("admin123@")) {
        if (adminService.authenticateUser(email, password)) {
            session.setAttribute("userEmail", email);
            return "redirect:/Frro/dashboard"; // Redirect to the dashboard on successful admin login
        } 
//        else if (email.equals("head@gmail.com") && password.equals("head123@")) {
        else if (hodService.authenticateUser(email, password)) {
            session.setAttribute("userEmail", email);
            return handleHodLogin(session, model);
        } else {
            model.addAttribute("error", "Invalid email or password. Access denied.");
            return "adminlogin";
        }
    }

    @PostMapping("/filterByDate")
    public String filterByDate(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fromDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate,
            HttpSession session,
            Model model) {

        String email = (String) session.getAttribute("userEmail");
        if (email == null) {
            model.addAttribute("error", "You must be logged in to filter data.");
            return "adminlogin";
        }

        if (email.equals("admin@gmail.com")) {
            return handleDateFilteringAdmin(fromDate, endDate, model);
        } else if (email.equals("head@gmail.com")) {
            return handleDateFilteringHod(fromDate, endDate, model);
        } else {
            model.addAttribute("error", "Invalid email or password. Access denied.");
            return "adminlogin";
        }
    }

    // private String handleAdminLogin(HttpSession session, Model model,LocalDate fromDate,LocalDate fromDate) {
    //     LocalDate today = LocalDate.now();
    //    if(fromDate==null && endDate == null) {
            
    //         List<VisitorEntry> visitors = adminService.getAllVisitors(today, today);

    //         long openCount = visitors.stream()
    //                 .filter(visitor -> visitor.getSolver_name() == null || visitor.getSolver_name().isEmpty())
    //                 .count();

    //         long closeCount = visitors.stream()
    //                 .filter(visitor -> visitor.getSolver_name() != null && !visitor.getSolver_name().isEmpty())
    //                 .count();
            
    //         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    //         model.addAttribute("emaiil", adminService.ADMIN_EMAIL);

    //         model.addAttribute("openCount", openCount);
    //         model.addAttribute("closeCount", closeCount);
    //         model.addAttribute("visitors", visitors); // Optional: Include visitor details if needed
    //         model.addAttribute("fromDate", today.format(formatter));
    //         model.addAttribute("endDate", today.format(formatter));

    //    	return "dashboard";
    //    }
        
    //     return handleDateFilteringAdmin(today, today, model);
    // }

    private String handleHodLogin(HttpSession session, Model model) {
    	 LocalDate today = LocalDate.now();
         return handleDateFilteringHod(today, today, model);
    }

    private String handleDateFilteringAdmin(LocalDate fromDate, LocalDate endDate, Model model) {
        try {
            if (fromDate == null) {
                fromDate = LocalDate.now();
            }
            if (endDate == null) {
                endDate = LocalDate.now();
            }

            if (fromDate.isAfter(endDate)) {
                model.addAttribute("error", "Start date cannot be after end date.");
                return "VisitorsList";
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedFromDate = fromDate.format(formatter);
            String formattedEndDate = endDate.format(formatter);

            List<VisitorEntry> filteredVisitors = adminService.getAllVisitors(fromDate, endDate);
            long openCount = filteredVisitors.stream()
                    .filter(visitor -> visitor.getSolver_name() == null || visitor.getSolver_name().isEmpty())
                    .count();
            long closeCount = filteredVisitors.stream()
                    .filter(visitor -> visitor.getSolver_name() != null && !visitor.getSolver_name().isEmpty())
                    .count();

            setVisitorImages(filteredVisitors);

            if (filteredVisitors.isEmpty()) {
                model.addAttribute("message", "No results found for the selected date range.");
            } else {
                model.addAttribute("visitors", filteredVisitors);
            }

            model.addAttribute("openCount", openCount);
            model.addAttribute("closeCount", closeCount);
            model.addAttribute("fromDate", formattedFromDate);
            model.addAttribute("endDate", formattedEndDate);
            model.addAttribute("email", adminService.ADMIN_EMAIL);

            return "VisitorsList";

        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while filtering dates: " + e.getMessage());
            return "VisitorsList";
        }
    }
    private String handleDateFilteringHod(LocalDate fromDate, LocalDate endDate, Model model) {

    	//System.out.println(fromDate+" to "+endDate);
            try {
                if (fromDate == null) {
                    fromDate = LocalDate.now();
                }
                if (endDate == null) {
                    endDate = LocalDate.now();
                }

                if (fromDate.isAfter(endDate)) {
                    model.addAttribute("error", "Start date cannot be after end date.");
                    return "reports";
                }

            // Validate the date range
            if (endDate.isBefore(fromDate)) {
                model.addAttribute("message", "End date cannot be before start date.");
                return "reports";
            }

            // Handle the case where startDate and endDate are the same
            LocalDateTime startDateTime = fromDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
       List<PurposeCount> filterVisitPurposes = hodService.getTotalVisitorsByPurposeAndDateRange(startDateTime, endDateTime);


            if (filterVisitPurposes.isEmpty()) {
                model.addAttribute("message", "No results found for the selected date range.");
            } else {
                model.addAttribute("purposeCounts", filterVisitPurposes);
            }

            // Format the dates for display
            model.addAttribute("fromDate", fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            model.addAttribute("endDate", endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            model.addAttribute("email", hodService.Hod_EMAIL);

            return "reports";

            } catch (Exception e) {
            model.addAttribute("error", "An error occurred while filtering dates: " + e.getMessage());
            return "reports";
         }
    }
        

    private void setVisitorImages(List<VisitorEntry> visitors) {
        String defaultImageUrl = "src/main/resources/static/logo/default.png";
        String defaultBase64Image = null;

        // Convert the default image to Base64 once
        try {
            byte[] defaultImageBytes = Files.readAllBytes(Paths.get(defaultImageUrl));
            defaultBase64Image = Base64.getEncoder().encodeToString(defaultImageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        for (VisitorEntry visitor : visitors) {
            if (visitor.getVisitor_img() != null && visitor.getVisitor_img().length > 0) {
                String base64Image = Base64.getEncoder().encodeToString(visitor.getVisitor_img());
                visitor.setImageBase64(base64Image);
            } else {
                visitor.setImageBase64(defaultBase64Image);
            }
        }
    }

    @PostMapping("/updateRemarksAjax")
    public ResponseEntity<Map<String, Object>> updateVisitorRemarksAjax(@ModelAttribute("visitorEntry") VisitorEntry visitorEntry,
                                                                        BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            response.put("status", "error");
            response.put("message", "Validation errors occurred");
            return ResponseEntity.badRequest().body(response);
        }
        try {
            remarkService.updateSolverName(visitorEntry.getVisitor_full_name(), visitorEntry.getSolver_name(),
                    visitorEntry.getApplicant_ppt_no(), visitorEntry.getVisitor_remarks());
            response.put("status", "success");
            response.put("message", "Visitor Remarks updated successfully!!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "An error occurred while updating the remarks. Try Again Later");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @SuppressWarnings("static-access")
   	@GetMapping("/authenticate")
       public String showVisitorsList(Model model) {
           LocalDate today = LocalDate.now();
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
           
           // Format the dates
           String formattedToday = today.format(formatter);
           
           List<VisitorEntry> visitors = adminService.getAllVisitors(today, today);
           List<VisitorRemarks> allRemarks = remarkService.getAllRemarks();

           // Create a map for quick lookup of solver names by visitor name 
           Map<String, String> solverNameMap = new HashMap<>();
           for (VisitorEntry visitor : visitors) {
               String visitorName = visitor.getVisitor_full_name();
               String solverName = visitor.getSolver_name();

               if (visitorName != null && solverName != null) {
                   solverNameMap.put(visitorName.trim(), solverName.trim());
               }
           }

           // Map solver names to visitors
           for (VisitorEntry visitor : visitors) {
               String visitorName = visitor.getVisitor_full_name().trim();
               String solverName = solverNameMap.get(visitorName);
               visitor.setSolver_name(solverName);
           }
           long openCount = visitors.stream()
                   .filter(visitor -> visitor.getSolver_name() == null || visitor.getSolver_name().isEmpty())
                   .count();

           long closeCount = visitors.stream()
                   .filter(visitor -> visitor.getSolver_name() != null && !visitor.getSolver_name().isEmpty())
                   .count();
           setVisitorImages(visitors);   
           model.addAttribute("openCount", openCount);
           model.addAttribute("closeCount", closeCount);

           model.addAttribute("visitorEntry", new VisitorEntry());
           model.addAttribute("visitorRemarks", new VisitorRemarks());
           model.addAttribute("visitors", visitors);
           
           model.addAttribute("fromDate", formattedToday);
           model.addAttribute("endDate", formattedToday);
           model.addAttribute("email", adminService.ADMIN_EMAIL);

           return "VisitorsList";	
       }
       @GetMapping("/dashboard")
       public String showDashboard(HttpSession session, Model model) {
           String email = (String) session.getAttribute("userEmail");
   
           if (email == null) {
               model.addAttribute("error", "You must be logged in to view the dashboard.");
               return "adminlogin";
           }
   
           LocalDate today = LocalDate.now();
           List<VisitorEntry> visitors = adminService.getAllVisitors(today, today);
   
           long openCount = visitors.stream()
                   .filter(visitor -> visitor.getSolver_name() == null || visitor.getSolver_name().isEmpty())
                   .count();
   
           long closeCount = visitors.stream()
                   .filter(visitor -> visitor.getSolver_name() != null && !visitor.getSolver_name().isEmpty())
                   .count();
   
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
           model.addAttribute("email", adminService.ADMIN_EMAIL);
           model.addAttribute("openCount", openCount);
           model.addAttribute("closeCount", closeCount);
           model.addAttribute("visitors", visitors); // Optional: Include visitor details if needed
           model.addAttribute("fromDate", today.format(formatter));
           model.addAttribute("endDate", today.format(formatter));
   
           return "dashboard"; // Render the dashboard page
       }
   }
    

