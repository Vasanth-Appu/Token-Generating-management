package com.example.frro.controller;

import com.example.frro.entity.Token;
import com.example.frro.service.TokenService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class ReprintTokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/reprint")
    @ResponseBody
    public ResponseEntity<String> getToken(@RequestParam("passportNo") String passportNo,
                                           @RequestParam("contactNumber") String contactNumber) {
        Token token = tokenService.findByPassportAndContact(passportNo, contactNumber);

        if (token != null) {
            // Local directory to save PDF
            String localDirectory = "f:/generated_pdfs/";
            String pdfFilename = "Token_" + token.getTokenId() + "_" + UUID.randomUUID().toString() + ".pdf";
            Path pdfPath = Paths.get(localDirectory, pdfFilename);

            try {
                // Create directories if they don't exist
                Files.createDirectories(pdfPath.getParent());

                // Generate PDF and save it locally
                try (PdfWriter writer = new PdfWriter(Files.newOutputStream(pdfPath));
                     PdfDocument pdf = new PdfDocument(writer);
                     Document document = new Document(pdf)) {

                    document.add(new Paragraph("Token Reprinted....."));
                    document.add(new Paragraph("Token ID: " + token.getTokenId()));
                    document.add(new Paragraph("Passport No: " + passportNo));
                    document.add(new Paragraph("Contact Number: " + contactNumber));
                    // Add more details as necessary
                }

                return ResponseEntity.ok("Token exists: " + token.getTokenId() + "      Please Collect Your Token");

            } catch (Exception e) {
                return ResponseEntity.status(500).body("Failed to generate PDF: " + e.getMessage());
            }

        } else {
            return ResponseEntity.status(404).body("Token does not exist (OR) Already closed your Issue");
        }
    }
}
