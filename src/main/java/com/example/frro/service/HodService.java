package com.example.frro.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.frro.entity.PurposeCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class HodService {
    private static final Logger logger = LoggerFactory.getLogger(HodService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String Hod_EMAIL = "head@gmail.com";
    public static final String Hod_PASSWORD = "head123@";

    public boolean authenticateUser(String email, String password) {
        return Hod_EMAIL.equalsIgnoreCase(email.trim()) && Hod_PASSWORD .equals(password.trim());
    }

    public List<PurposeCount> getTotalVisitorsByPurposeAndDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        // Validate and log the input dates
        if (startDateTime == null) {
            throw new IllegalArgumentException("startDateTime cannot be null");
        }
        if (endDateTime == null) {
            throw new IllegalArgumentException("endDateTime cannot be null");
        }
        if (startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("startDateTime must be before or equal to endDateTime");
        }

       // logger.info("Fetching total visitors from {} to {}", startDateTime, endDateTime);

        String sql = "SELECT " +
                "p.visitor_visit_purpose_desc AS purpose, " +
                "COALESCE(COUNT(v.visitor_gen_id), 0) AS total, " +
                "CASE " +
                "    WHEN COUNT(v.visitor_gen_id) = 0 THEN 0 " +
                "    ELSE MAX(CASE WHEN v.solver_name IS NULL THEN 1 ELSE 0 END) " +
                "END AS openCount, " +
                "CASE " +
                "    WHEN COUNT(v.visitor_gen_id) = 0 THEN 0 " +
                "    ELSE MAX(CASE WHEN v.solver_name IS NOT NULL THEN 1 ELSE 0 END) " +
                "END AS closedCount " +
                "FROM " +
                "m_svgm_visit_purpose p " +
                "LEFT OUTER JOIN " +
                "t_svgm_frro_visitors v " +
                "ON p.visitor_visit_purpose_id = v.visitor_visit_purpose_id " +
                "AND v.rec_entry_timestamp BETWEEN ? AND ? " +
                "GROUP BY " +
                "p.visitor_visit_purpose_desc " +
                "ORDER BY " +
                "p.visitor_visit_purpose_desc";


        try {
            return jdbcTemplate.query(sql, ps -> {
                ps.setTimestamp(1, java.sql.Timestamp.valueOf(startDateTime));
                ps.setTimestamp(2, java.sql.Timestamp.valueOf(endDateTime));
            }, (rs, rowNum) -> {
                PurposeCount purposeCount = new PurposeCount();
                purposeCount.setPurpose(rs.getString("purpose"));
                purposeCount.setTotal(rs.getInt("total"));
                purposeCount.setOpenCount(rs.getInt("openCount"));
                purposeCount.setClosedCount(rs.getInt("closedCount"));
                return purposeCount;
            });
        } catch (Exception e) {
            logger.error("Error executing query: ", e);
            throw e; // or handle the exception as needed
        }
    }
}
