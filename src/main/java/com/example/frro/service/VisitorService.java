package com.example.frro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.frro.entity.VisitorEntry;
import com.example.frro.util.Util;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class VisitorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(VisitorService.class.getName());

    public List<String> getCountries() {
        String sql = "SELECT country_name FROM m_svgm_country ORDER BY country_name ASC";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<String> getCountrycode() {
        String sql = "SELECT country_code_num, country_name FROM m_country_code_org ORDER BY country_name ASC";
        List<String> codenames = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> row : rows) {
            String countryCode = (String) row.get("country_code_num");
            String countryName = (String) row.get("country_name");
            codenames.add(countryName + " ("  +"+"+ countryCode + ")");
        }
        return codenames;
    }

	
	
	 public List<String> getVisitorPurpose() { String sql =
	  "SELECT visitor_visit_purpose_desc FROM m_svgm_visit_purpose WHERE active='Y'"
	 ; return jdbcTemplate.queryForList(sql, String.class); }
	 
	 

    @Transactional
    public Map<String, String> saveVisitor(VisitorEntry visitorEntry) {

        String genId = Util.generateGenId(jdbcTemplate);
        Short dailyToken = Util.generateDailyToken(jdbcTemplate);
        String frroCode = "B002";
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currentDateTime);
        String countryCode;
        String purpose;

        // Fetch country code
        try {
            String countryQuery = "SELECT country_code FROM public.m_svgm_country WHERE country_name = ?";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(countryQuery, visitorEntry.getApplicant_nationality());

            if (result.isEmpty()) {
                throw new RuntimeException("Nationality not found in m_svgm_country: " + visitorEntry.getApplicant_nationality());
            }
            countryCode = (String) result.get(0).get("country_code");
        } catch (Exception e) {
            throw new RuntimeException("Error fetching country code", e);
        }
        try {
        	String visitPurposeQuery = "SELECT UPPER(visitor_visit_purpose_desc) AS visitor_visit_purpose_desc FROM public.m_svgm_visit_purpose WHERE visitor_visit_purpose_id = ?";

            // Use the Short directly for visitor_visit_purpose_id
            Short visitPurposeId = visitorEntry.getVisitor_visit_purpose_id();

            List<Map<String, Object>> purposeResult = jdbcTemplate.queryForList(visitPurposeQuery, visitPurposeId);

            if (purposeResult.isEmpty()) {
                throw new RuntimeException("Visitor purpose not found in m_svgm_visit_purpose: " + visitPurposeId);
            }
            purpose = (String) purposeResult.get(0).get("visitor_visit_purpose_desc");
            //System.out.println("Fetched Purpose: " + purpose); // Debugging output

        } catch (Exception e) {
            throw new RuntimeException("Error fetching visitor purpose", e);
        }
        visitorEntry.setVisit_purpose_other_desc(purpose);


        
        
        String checkQuery = "SELECT visitor_gen_id FROM public.t_svgm_frro_visitors WHERE visitor_full_name = ? AND applicant_ppt_no = ? AND visitor_visit_date = ?";
        List<Map<String, Object>> existingEntries = jdbcTemplate.queryForList(checkQuery, visitorEntry.getVisitor_full_name(), visitorEntry.getApplicant_ppt_no(),timestamp);

        if (!existingEntries.isEmpty()) {
            String existingGenId = (String) existingEntries.get(0).get("visitor_gen_id");
           // LOGGER.log(Level.INFO, "Record with similar details already exists with genId {0}", existingGenId);
            return Map.of("genId", existingGenId); // Return the existing genId if a record with similar details is found
            }
        // Use try-with-resources to ensure resources are closed
			        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
			             CallableStatement stmt = connection.prepareCall("{CALL insert_visitor(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
			
			            
			        	
			        	
			        	
			        	
			        	
			        	// Validate and set parameters
			            stmt.setString(1, genId);
			            stmt.setShort(2, dailyToken);
			            stmt.setString(3, frroCode);
			            stmt.setString(4, visitorEntry.getVisitor_full_name());
			            stmt.setShort(5, visitorEntry.getVisitor_id_type_id());
			            stmt.setString(6, visitorEntry.getVisitor_id_type());
			            stmt.setString(7, visitorEntry.getVisitor_address());
			            stmt.setString(8, visitorEntry.getApplicant_full_name());
			            stmt.setString(9, visitorEntry.getApplicant_ppt_no());
			            stmt.setString(10, countryCode);
			            stmt.setString(11, visitorEntry.getVisitor_contact_no_india());
			            stmt.setString(12, visitorEntry.getVisitor_country_code_num());
			            stmt.setString(13, visitorEntry.getVisitor_contact_no_intnl());
			            stmt.setShort(14, visitorEntry.getVisitor_visit_purpose_id());
			            stmt.setString(15, purpose);
			          //  stmt.setString(15, visitorEntry.getVisit_purpose_other_desc());
			            stmt.setDate(16, java.sql.Date.valueOf(currentDateTime.toLocalDate()));
			            stmt.setTimestamp(17, timestamp);

			            if (visitorEntry.getVisitor_img() != null) {
			                stmt.setBytes(18, visitorEntry.getVisitor_img());
			            } else {
			                stmt.setNull(18, Types.BINARY);
			            }
			
			            // Execute the procedure
			            stmt.execute();
			            // Format the current date
			            String formattedDate = currentDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			            return Map.of("dailyToken", dailyToken.toString(), "currentDate", formattedDate);
			     			        } catch (SQLException e) {
			            // Log the error for debugging
			            LOGGER.log(Level.SEVERE, "Error executing PostgreSQL function", e);
			            throw new RuntimeException("Error executing PostgreSQL function", e);
			        }
    }



	

	


	}
