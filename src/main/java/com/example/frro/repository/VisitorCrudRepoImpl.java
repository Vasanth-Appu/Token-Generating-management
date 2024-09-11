/*package com.example.frro.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.frro.repository.IVisitorCrudRepo;
import com.example.frro.dao.VisitorDetailFmtOneDto;
import com.example.frro.dao.VisitorDetailFrroUpdDto;
import com.example.frro.entity.VisitorAddNewReq;
@Repository
public class VisitorCrudRepoImpl implements IVisitorCrudRepo
{
	private static final class VisitorDetailFmtOneMapper implements RowMapper<VisitorDetailFmtOneDto>
	{
		@Override
		public VisitorDetailFmtOneDto mapRow(ResultSet objResutSet, int rowNum) throws SQLException
		{
			VisitorDetailFmtOneDto objVisitorDetailFmtOneDto = new VisitorDetailFmtOneDto();
			objVisitorDetailFmtOneDto.setRow_sl_no(objResutSet.getString("row_sl_no"));
			objVisitorDetailFmtOneDto.setVisitor_auto_id(objResutSet.getString("visitor_auto_id"));
			objVisitorDetailFmtOneDto.setVisitor_gen_id(objResutSet.getString("visitor_gen_id"));
			objVisitorDetailFmtOneDto.setApplicant_full_name(objResutSet.getString("applicant_full_name"));
			objVisitorDetailFmtOneDto.setApplicant_ppt_no(objResutSet.getString("applicant_ppt_no"));
			objVisitorDetailFmtOneDto.setCountry_name(objResutSet.getString("country_name"));
			objVisitorDetailFmtOneDto.setVisitor_contact_no(objResutSet.getString("visitor_contact_no"));
			objVisitorDetailFmtOneDto.setVisitor_visit_purpose_desc(objResutSet.getString("visitor_visit_purpose_desc"));
			objVisitorDetailFmtOneDto.setVisitor_visit_date(objResutSet.getString("visitor_visit_date"));
			return objVisitorDetailFmtOneDto;
		}
	}

	private static final class VisitorDetailFrroUpdMapper implements RowMapper<VisitorDetailFrroUpdDto>
	{
		@Override
		public VisitorDetailFrroUpdDto mapRow(ResultSet objResutSet, int rowNum) throws SQLException
		{
			VisitorDetailFrroUpdDto objVisitorDetailFrroUpdDto = new VisitorDetailFrroUpdDto();
			objVisitorDetailFrroUpdDto.setVisitor_auto_id(objResutSet.getString("visitor_auto_id"));
			objVisitorDetailFrroUpdDto.setVisitor_gen_id(objResutSet.getString("visitor_gen_id"));
			objVisitorDetailFrroUpdDto.setVisitor_full_name(objResutSet.getString("visitor_full_name"));
			objVisitorDetailFrroUpdDto.setVisitor_id_type_id(objResutSet.getShort("visitor_id_type_id"));
			objVisitorDetailFrroUpdDto.setVisitor_id_type(objResutSet.getString("visitor_id_type"));
			objVisitorDetailFrroUpdDto.setVisitor_address(objResutSet.getString("visitor_address"));
			objVisitorDetailFrroUpdDto.setApplicant_full_name(objResutSet.getString("applicant_full_name"));
			objVisitorDetailFrroUpdDto.setApplicant_ppt_no(objResutSet.getString("applicant_ppt_no"));
			objVisitorDetailFrroUpdDto.setApplicant_nationality(objResutSet.getString("applicant_nationality"));
			objVisitorDetailFrroUpdDto.setVisitor_contact_no_india(objResutSet.getString("visitor_contact_no_india"));
			objVisitorDetailFrroUpdDto.setVisitor_country_code_num(objResutSet.getString("visitor_country_code_num"));
			objVisitorDetailFrroUpdDto.setVisitor_contact_no_intnl(objResutSet.getString("visitor_contact_no_intnl"));
			objVisitorDetailFrroUpdDto.setVisitor_visit_purpose_id(objResutSet.getShort("visitor_visit_purpose_id"));
			objVisitorDetailFrroUpdDto.setVisit_purpose_other_desc(objResutSet.getString("visit_purpose_other_desc"));
			objVisitorDetailFrroUpdDto.setFrro_action_status_id(objResutSet.getString("frro_action_status_id"));
			objVisitorDetailFrroUpdDto.setVisitor_qry_action_remark(objResutSet.getString("visitor_qry_action_remark"));
			return objVisitorDetailFrroUpdDto;
		}
	}

	private Logger objLogger = LoggerFactory.getLogger(getClass());

	private final String SQL_SAVE_FRRO_VISITOR = "select svgm_visitor_visit_ins (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private final String SQL_GET_ALL_FRRO_VISITOR = "SELECT ROW_NUMBER () OVER (ORDER BY tsfv.visitor_auto_id DESC) AS row_sl_no, CAST (tsfv.visitor_auto_id AS TEXT) AS visitor_auto_id, tsfv.visitor_gen_id, tsfv.applicant_full_name, tsfv.applicant_ppt_no, msc.country_name, tsfv.visitor_contact_no_india AS visitor_contact_no, msvp.visitor_visit_purpose_desc, to_char(tsfv.visitor_visit_date, 'DD/MM/YYYY')  || '<br/>' || to_char(tsfv.rec_entry_timestamp, 'HH24:MI') AS visitor_visit_date FROM public.t_svgm_frro_visitors tsfv INNER JOIN public.m_svgm_country msc ON tsfv.applicant_nationality = msc.country_code INNER JOIN public.m_svgm_visit_purpose msvp ON tsfv.visitor_visit_purpose_id = msvp.visitor_visit_purpose_id INNER JOIN t_svgm_frro_action_status tsfas ON tsfv.visitor_auto_id = tsfas.visitor_auto_id WHERE tsfv.visitor_frro_fro_code = 'B002' AND tsfas.frro_action_status_id = 1 ORDER BY tsfv.visitor_auto_id DESC;";

	private final String SQL_GET_FRRO_VISITOR_BY_AUTO_GEN_VSTDATE = "SELECT CAST (tsfv.visitor_auto_id AS TEXT) AS visitor_auto_id, tsfv.visitor_gen_id, tsfv.visitor_full_name, tsfv.visitor_id_type_id, tsfv.visitor_id_type, tsfv.visitor_address, tsfv.applicant_full_name, tsfv.applicant_ppt_no, tsfv.applicant_nationality, tsfv.visitor_contact_no_india, tsfv.visitor_country_code_num, tsfv.visitor_contact_no_intnl, CAST (tsfv.visitor_visit_purpose_id AS TEXT) AS visitor_visit_purpose_id, tsfv.visit_purpose_other_desc, tsfas.frro_action_status_id, tsfas.visitor_qry_action_remark FROM public.t_svgm_frro_visitors tsfv INNER JOIN t_svgm_frro_action_status tsfas ON tsfv.visitor_auto_id = tsfas.visitor_auto_id WHERE CAST (tsfv.visitor_auto_id AS TEXT) = ? AND tsfv.visitor_gen_id = ?;";

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<VisitorDetailFmtOneDto> getAllFrroVisitors() throws SQLException
	{
		try
		{
			this.objLogger.info("-:: VisitorCrudRepoImpl::-" + " --> " + "getAllFrroVisitors()");

			return this.jdbcTemplate.query(this.SQL_GET_ALL_FRRO_VISITOR, new VisitorDetailFmtOneMapper());
		}
		catch (EmptyResultDataAccessException e)
		{
			return null;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	@Override
	public VisitorDetailFrroUpdDto getVisitorDetailByAutoNdGenIdNdVstDate(String visitor_auto_id, String visitor_gen_id, String visitor_visit_date) throws SQLException
	{
		try
		{
			this.objLogger.info("-:: VisitorCrudRepoImpl::-" + " --> " + "getVisitorDetailByAutoNdGenIdNdVstDate()");
			Object[] parameters = new Object[] { visitor_auto_id, visitor_gen_id };
			return this.jdbcTemplate.queryForObject(this.SQL_GET_FRRO_VISITOR_BY_AUTO_GEN_VSTDATE, new VisitorDetailFrroUpdMapper(), parameters);
		}
		catch (EmptyResultDataAccessException e)
		{
			return null;
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public String saveFrroVisitor(VisitorAddNewReq objVisitorAddNewReq) throws SQLException
	{
		try
		{
			this.objLogger.info("-:: VisitorCrudRepoImpl::-" + " --> " + "saveFrroVisitor()");

			//@formatter:off
			String strReturnVal = this.jdbcTemplate.queryForObject(this.SQL_SAVE_FRRO_VISITOR, new Object[]
			{
				objVisitorAddNewReq.getVisitor_frro_fro_code(),
				objVisitorAddNewReq.getVisitor_full_name(),
				objVisitorAddNewReq.getVisitor_id_type_id(),
				objVisitorAddNewReq.getVisitor_id_type(),
				objVisitorAddNewReq.getVisitor_address(),
				objVisitorAddNewReq.getApplicant_full_name(),
				objVisitorAddNewReq.getApplicant_ppt_no(),
				objVisitorAddNewReq.getApplicant_nationality(),
				objVisitorAddNewReq.getVisitor_contact_no_india(),
				objVisitorAddNewReq.getVisitor_country_code_num(),
				objVisitorAddNewReq.getVisitor_contact_no_intnl(),
				objVisitorAddNewReq.getVisitor_visit_purpose_id(),
				objVisitorAddNewReq.getVisit_purpose_other_desc()
			}, String.class);
			return strReturnVal;
			//@formatter:on
		}
		catch (EmptyResultDataAccessException e)
		{
			return null;
		}
	}

}
*/