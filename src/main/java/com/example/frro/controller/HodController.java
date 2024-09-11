//package com.example.frro.controller;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.example.frro.entity.PurposeCount;
//import com.example.frro.service.HodService;
//
//@Controller
//@RequestMapping("/Frro")
//public class HodController {
//
//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//
//    @Autowired
//    private HodService hodService;
//
//    @GetMapping("/adminlogin")
//    public String getHodPage() {
//        return "adminlogin";
//    }
//
//    @PostMapping("/hodauth")
//    public String processRequest(
//            @RequestParam(value = "email", required = false) String email,
//            @RequestParam(value = "password", required = false) String password,
//            @RequestParam(value = "startDate", required = false) String startDateStr,
//            @RequestParam(value = "endDate", required = false) String endDateStr,
//            Model model) {
//
//        LocalDate startDate = null;
//        LocalDate endDate = null;
//
//        try {
//            if (startDateStr != null && !startDateStr.isEmpty()) {
//                startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//            }
//            if (endDateStr != null && !endDateStr.isEmpty()) {
//                endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//            }
//        } catch (DateTimeParseException e) {
//            model.addAttribute("error", "Invalid date format. Please use dd/MM/yyyy.");
//            return "hodlogin"; // or another view to display the error
//        }
//
//        if (email != null && password != null) {
//            return handleLogin(email, password, model);
//        } else if (startDate != null || endDate != null) {
//            return handleDateFiltering(startDate, endDate, model);
//        } else {
//            model.addAttribute("error", "Missing parameters.");
//            return "hodlogin";
//        }
//    }
//
//
//    private String handleDateFiltering(LocalDate startDate, LocalDate endDate, Model model) {
//        // Check if both startDate and endDate are provided
//        if (startDate == null && endDate == null) {
//            model.addAttribute("datemessage", "Please provide both start date and end date.");
//            return "reports";
//        }
//
//        // Use the current date if either startDate or endDate is missing
//        if (startDate == null || endDate == null) {
//            LocalDate today = LocalDate.now();
//            startDate = (startDate == null) ? today : startDate;
//            endDate = (endDate == null) ? today : endDate;
//        }
//
//        // Validate the date range
//        if (endDate.isBefore(startDate)) {
//            model.addAttribute("message", "End date cannot be before start date.");
//            return "reports";
//        }
//
//        // Handle the case where startDate and endDate are the same
//        LocalDateTime startDateTime = startDate.atStartOfDay();
//        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
//
//        List<PurposeCount> filterVisitPurposes = hodService.getTotalVisitorsByPurposeAndDateRange(startDateTime, endDateTime);
//
//        if (filterVisitPurposes.isEmpty()) {
//            model.addAttribute("message", "No results found for the selected date range.");
//        } else {
//            model.addAttribute("purposeCounts", filterVisitPurposes);
//        }
//
//        // Format the dates for display
//        model.addAttribute("fromDate", startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//        model.addAttribute("endDate", endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//        model.addAttribute("email",hodService.Hod_EMAIL);
//
//
//        return "reports";
//    }
//
//    private String handleLogin(String email, String password, Model model) {
//        boolean isAuthenticated = hodService.authenticateUser(email, password);
//        LocalDate today = LocalDate.now();
//
//        if (isAuthenticated) {
//            // Get visitor purposes for today
//            List<PurposeCount> visitPurposes = hodService.getTotalVisitorsByPurposeAndDateRange(today.atStartOfDay(), today.atTime(LocalTime.MAX));
//            model.addAttribute("purposeCounts", visitPurposes);
//
//            // Format today's date as a string using the FORMATTER
//            String formattedDate =  today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//
//            // Set today's date as both fromDate and endDate
//            model.addAttribute("fromDate", formattedDate);
//            model.addAttribute("endDate", formattedDate);
//           // System.out.println(formattedDate);
//            model.addAttribute("email",hodService.Hod_EMAIL);
//
//            return "reports";
//        } else {
//            model.addAttribute("error", "Invalid email or password.");
//            return "hodlogin";
//        }
//    }
//
//}
