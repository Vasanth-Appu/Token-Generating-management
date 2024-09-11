package com.example.frro.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.frro.entity.VisitorEntry;

public class VisitorRowMapper implements RowMapper<VisitorEntry> {

	@Override
	public VisitorEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
        VisitorEntry visitor = new VisitorEntry();
        visitor.setGenId(rs.getString("visitor_gen_id"));
        visitor.setDailyToken(rs.getShort("visitor_daily_token_no"));
        visitor.setVisitor_frro_fro_code(rs.getString("visitor_frro_fro_code"));
        visitor.setVisitor_full_name(rs.getString("visitor_full_name"));
        visitor.setVisitor_id_type_id(rs.getShort("visitor_id_type_id"));
        visitor.setVisitor_id_type(rs.getString("visitor_id_type"));
        visitor.setVisitor_address(rs.getString("visitor_address"));
        visitor.setApplicant_full_name(rs.getString("applicant_full_name"));
        visitor.setApplicant_ppt_no(rs.getString("applicant_ppt_no"));
        visitor.setApplicant_nationality(rs.getString("applicant_nationality"));
        visitor.setVisitor_contact_no_india(rs.getString("visitor_contact_no_india"));
        visitor.setVisitor_country_code_num(rs.getString("visitor_country_code_num"));
        visitor.setVisitor_contact_no_intnl(rs.getString("visitor_contact_no_intnl"));
        visitor.setVisitor_visit_purpose_id(rs.getShort("visitor_visit_purpose_id"));
        visitor.setVisit_purpose_other_desc(rs.getString("visit_purpose_other_desc"));
        visitor.setTimestamp(rs.getTimestamp("rec_entry_timestamp"));
        visitor.setImageBase64(rs.getString("image_base64"));
        return visitor;
    }

}




