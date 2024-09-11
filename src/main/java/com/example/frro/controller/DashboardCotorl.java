// package com.example.frro.controller;

// import java.time.LocalDate;
// import java.util.List;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.example.frro.entity.VisitorEntry;
// import com.example.frro.service.AdminService;

// @Controller
// @RequestMapping("/Frro")
// public class DashboardCotorl {

//     private final AdminService adminService;

//     // Constructor injection for AdminService
//     public DashboardCotorl(AdminService adminService) {
//         this.adminService = adminService;
//     }

//     @GetMapping("/dashboard")
//     public String showDashboard(Model model) {
//         LocalDate today = LocalDate.now();
        
//         // Fetch all visitors for today
//         List<VisitorEntry> visitors = adminService.getAllVisitors(today, today);

//         // Calculate open and closed counts
//         long openCount = visitors.stream()
//                 .filter(visitor -> visitor.getSolver_name() == null || visitor.getSolver_name().isEmpty())
//                 .count();

//         long closeCount = visitors.stream()
//                 .filter(visitor -> visitor.getSolver_name() != null && !visitor.getSolver_name().isEmpty())
//                 .count();

//         // Add attributes to the model
//         model.addAttribute("emaiil", adminService.ADMIN_EMAIL);

//         model.addAttribute("openCount", openCount);
//         model.addAttribute("closeCount", closeCount);
//         model.addAttribute("visitors", visitors); // Optional: Include visitor details if needed
//         model.addAttribute("fromDate", today);
//         model.addAttribute("endDate", today);

//         return "dashboard";
//     }
// }

