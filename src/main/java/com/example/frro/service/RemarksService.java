package com.example.frro.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.example.frro.entity.VisitorRemarks;

import jakarta.transaction.Transactional;

@Service
public class RemarksService {
	@Autowired JdbcTemplate jdbcTemplate;
	
	@Transactional
	/*
	 * public VisitorRemarks saveRemarks(VisitorRemarks visitorRemarks) { String sql
	 * =
	 * "INSERT INTO public.remarks(visitor_name, visitor_ppt, visitor_remarks, solver_name, time_date) VALUES (?, ?, ?, ?, ?)"
	 * ;
	 * 
	 * jdbcTemplate.update(sql, visitorRemarks.getVisitor_name(),
	 * visitorRemarks.getVisitor_ppt(), visitorRemarks.getVisitor_remarks(),
	 * visitorRemarks.getSolver_name(), new
	 * java.sql.Timestamp(System.currentTimeMillis()) ); return visitorRemarks;
	 */
	//}
	
	public void updateSolverName(String visitorFullName, String solverName, String passport, String remarks) {
	    String sql = "UPDATE public.t_svgm_frro_visitors " +
	                 "SET solver_name = ?, visitor_remarks = ? " +
	                 "WHERE visitor_full_name = ? AND applicant_ppt_no = ?";

	    jdbcTemplate.update(sql, solverName, remarks, visitorFullName, passport);
	}
	
	  public boolean hasRemarks(String solverName) {
	        String sql = "SELECT COUNT(*) FROM remarks WHERE solver_name = ?";
	        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{solverName}, Integer.class);
	        return count != null && count > 0;
	    }
	  public List<VisitorRemarks> getAllRemarks() {
		    String sql = "SELECT visitor_name, visitor_ppt, visitor_remarks, solver_name, time_date FROM remarks";
		    return jdbcTemplate.query(sql, new RowMapper<VisitorRemarks>() {
		        @Override
		        public VisitorRemarks mapRow(ResultSet rs, int rowNum) throws SQLException {
		            VisitorRemarks remark = new VisitorRemarks();
		            remark.setVisitor_name(rs.getString("visitor_name"));
		            remark.setVisitor_ppt(rs.getString("visitor_ppt"));
		            remark.setVisitor_remarks(rs.getString("visitor_remarks"));
		            remark.setSolver_name(rs.getString("solver_name"));
		            return remark;
		        }
		    });
		}
	  

	    @SuppressWarnings("deprecation")
		public List<VisitorRemarks> getRemarksByVisitor(String visitorName) {
	        String sql = "SELECT * FROM remarks WHERE visitor_name = ?";
	        return jdbcTemplate.query(sql, new Object[]{visitorName}, (rs, rowNum) -> {
	            VisitorRemarks remark = new VisitorRemarks();
	            remark.setVisitor_name(rs.getString("visitor_name"));
	            remark.setSolver_name(rs.getString("solver_name"));
	            return remark;
	        });
	    }

	


	    }

	


