//package com.example.frro.util;
//
//
//import org.springframework.jdbc.core.JdbcTemplate;
//
//public class Util {
//
//    public static String generateGenId(JdbcTemplate jdbcTemplate) {
//        String sql = "SELECT visitor_gen_id FROM public.t_svgm_frro_visitors ORDER BY visitor_gen_id DESC LIMIT 1";
//        String lastGenId = jdbcTemplate.queryForObject(sql, String.class);
//
//        if (lastGenId == null) {
//            // If no records exist, start with a default value
//            return "B002201123134838001";
//        }
//
//        // Extract the numeric part and increment it
//        String prefix = lastGenId.substring(0, lastGenId.length() - 3);
//        String numericPart = lastGenId.substring(lastGenId.length() - 3);
//        int incrementedNumericPart = Integer.parseInt(numericPart) + 1;
//
//        // Construct the new genid
//        String newGenId = prefix + String.format("%03d", incrementedNumericPart);
//        return newGenId;
//    }
//    public static Short generateDailyToken(JdbcTemplate jdbcTemplate) {
//        // SQL to get the maximum daily token number
//        String sql = "SELECT COALESCE(MAX(visitor_daily_token_no), 0) FROM public.t_svgm_frro_visitors";
//
//        // Retrieve the last daily token number
//        Integer lastToken = jdbcTemplate.queryForObject(sql, Integer.class);
//
//        // If no token exists, start from 1
//        if (lastToken == null) {
//            lastToken = 0;
//        }
//
//        // Increment the token number
//        return (short) (lastToken + 1);
//    }
//}
//
package com.example.frro.util;

import java.time.LocalDate;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class Util {

	public static String generateGenId(JdbcTemplate jdbcTemplate) {
	    String sql = "SELECT visitor_gen_id FROM public.t_svgm_frro_visitors ORDER BY visitor_gen_id DESC LIMIT 1";
	    String lastGenId;

	    try {
	        lastGenId = jdbcTemplate.queryForObject(sql, String.class);
	    } catch (EmptyResultDataAccessException e) {
	        // If no records exist, start with a default value
	        return "B002201123134838001";
	    }

	    // Extract the numeric part and increment it
	    String prefix = lastGenId.substring(0, lastGenId.length() - 3);
	    String numericPart = lastGenId.substring(lastGenId.length() - 3);
	    int incrementedNumericPart = Integer.parseInt(numericPart) + 1;

	    // Construct the new genid
	    String newGenId = prefix + String.format("%03d", incrementedNumericPart);
	    return newGenId;
	}

//	public static Short generateDailyToken(JdbcTemplate jdbcTemplate) {
//	    // SQL to get the maximum daily token number
//	    String sql = "SELECT COALESCE(MAX(visitor_daily_token_no), 0) FROM public.t_svgm_frro_visitors";
//
//	    // Retrieve the last daily token number
//	    Integer lastToken;
//	    try {
//	        lastToken = jdbcTemplate.queryForObject(sql, Integer.class);
//	    } catch (EmptyResultDataAccessException e) {
//	        // If no token exists, start from 1
//	        lastToken = 0;
//	    }
//
//	    // Increment the last token number
//	    short newToken = (short) (lastToken + 1);
//	    return newToken;
//	}
	@SuppressWarnings("deprecation")
	public static Short generateDailyToken(JdbcTemplate jdbcTemplate) {
	    // SQL to get the maximum daily token number for the current date
	    String sql = "SELECT COALESCE(MAX(visitor_daily_token_no), 0) FROM public.t_svgm_frro_visitors WHERE token_date = ?";

	    LocalDate currentDate = LocalDate.now();
	    Integer lastToken;
	    try {
	        lastToken = jdbcTemplate.queryForObject(sql, new Object[]{java.sql.Date.valueOf(currentDate)}, Integer.class);
	    } catch (EmptyResultDataAccessException e) {
	        // If no token exists, start from 1
	        lastToken = 0;
	    }

	    // Increment the last token number
	    short newToken = (short) (lastToken + 1);
	    return newToken;
	}


}



