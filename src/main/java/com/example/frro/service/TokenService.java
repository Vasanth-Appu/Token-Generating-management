package com.example.frro.service;

import com.example.frro.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("deprecation")
	public Token findByPassportAndContact(String passportNo, String contactNumber) {
    	String sql = "SELECT visitor_daily_token_no FROM t_svgm_frro_visitors WHERE applicant_ppt_no = ? AND visitor_contact_no_india = ? AND solver_name IS NULL";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{passportNo, contactNumber}, (rs, rowNum) -> {
                Token token = new Token();
                token.setTokenId(rs.getString("visitor_daily_token_no"));  // Assuming visitor_daily_token_no is the token ID
                token.setPassportNo(passportNo);
                token.setContactNumber(contactNumber);
                // Set other fields as necessary
                return token;
            });
        } catch (Exception e) {
            return null; // No token found
        }
    }
}
