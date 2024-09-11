package com.example.frro.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.example.frro.entity.VisitorEntry;


@Service



public class AdminService {
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
    public static final String ADMIN_EMAIL = "admin@gmail.com";
    public static final String ADMIN_PASSWORD = "admin123@";

    public boolean authenticateUser(String email, String password) {
        return ADMIN_EMAIL.equalsIgnoreCase(email.trim()) && ADMIN_PASSWORD.equals(password.trim());
    }
   
    
//    public List<VisitorEntry> getAllVisitors() {
//        String sql = "SELECT v.visitor_gen_id AS genId, " +
//                     "v.visitor_daily_token_no AS dailyToken, " +
//                     "v.visitor_frro_fro_code AS frroCode, " +
//                     "v.visitor_full_name , " +
//                     "v.visitor_id_type_id , " +
//                     "v.visitor_id_type , " +
//                     "v.visitor_address , " +
//                     "v.applicant_full_name , " +
//                     "v.applicant_ppt_no , " +
//                     "c.country_name AS applicant_nationality, " +
//                     "v.visitor_contact_no_india, " +
//                     "v.visitor_country_code_num , " +
//                     "v.visitor_contact_no_intnl , " +
//                     "v.visitor_visit_purpose_id , " +
//                     "v.visit_purpose_other_desc , " +
//                     "v.rec_entry_timestamp AS timestamp, " +
//                     "i.visitor_img AS visitor_img " +
//                     "FROM t_svgm_frro_visitors v " +
//                     "LEFT JOIN visitor_img i ON v.visitor_gen_id = i.visitor_gen_id "+
//                     "LEFT JOIN m_svgm_country c ON v.applicant_nationality = c.country_code " +
//                     "ORDER BY v.visitor_gen_id";
//        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(VisitorEntry.class));
//    }
    public List<VisitorEntry> getAllVisitors(LocalDate fromDate, LocalDate endDate) {


        String sql = "SELECT v.visitor_gen_id AS genId, " +
                     "v.visitor_daily_token_no AS dailyToken, " +
                     "v.visitor_frro_fro_code AS frroCode, " +
                     "v.visitor_full_name , " +
                     "v.visitor_id_type_id , " +
                     "v.visitor_id_type , " +
                     "v.visitor_address , " +
                     "v.applicant_full_name , " +
                     "v.applicant_ppt_no , " +
                     "c.country_name AS applicant_nationality, " +
                     "v.visitor_contact_no_india, " +
                     "v.visitor_country_code_num , " +
                     "v.visitor_contact_no_intnl , " +
                     "v.visitor_visit_purpose_id , " +
                     "v.visit_purpose_other_desc , " +
                     "v.solver_name,"+
                     "v.visitor_remarks,"+
                     "v.rec_entry_timestamp AS timestamp, " +
                     "i.visitor_img AS visitor_img " +
                     "FROM t_svgm_frro_visitors v " +
                     "LEFT JOIN visitor_img i ON v.visitor_gen_id = i.visitor_gen_id "+
                     "LEFT JOIN m_svgm_country c ON v.applicant_nationality = c.country_code " +
                     "WHERE v.visitor_visit_date BETWEEN ? AND ? " +
                     "ORDER BY v.visitor_gen_id";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(VisitorEntry.class), fromDate, endDate);
    }


	 public boolean isAdmin(String email) {
        // Implement your logic to check if the user is an admin
        // This could be based on the email, role, or other criteria
        // For example:
        return "admin@gmail.com".equals(email); // Replace with actual admin check
    }


}


