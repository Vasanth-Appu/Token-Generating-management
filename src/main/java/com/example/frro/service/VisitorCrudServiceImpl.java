//package com.example.frro.service;
//
//
//
//import java.sql.SQLException;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import com.example.frro.repository.IVisitorCrudRepo;
//import com.example.frro.dao.VisitorDetailFmtOneDto;
//import com.example.frro.dao.VisitorDetailFrroUpdDto;
//
//import com.example.frro.entity.VisitorAddNewReq;
//
//@Service
//@Qualifier("visitorCrudServiceImpl")
//public  class VisitorCrudServiceImpl implements IVisitorCrudService
//{
//	private Logger objLogger = LoggerFactory.getLogger(getClass());
//
//	@Autowired
//	private IVisitorCrudRepo objVisitorCrudRepo;
//
//	@Override
//	public List<VisitorDetailFmtOneDto> getAllFrroVisitors() throws SQLException
//	{
//		this.objLogger.info("-:: VisitorCrudServiceImpl ::-" + " --> " + "getAllFrroVisitors()");
//		return this.objVisitorCrudRepo.getAllFrroVisitors();
//	}
//
//	@Override
//	public VisitorDetailFrroUpdDto getVisitorDetailByAutoNdGenIdNdVstDate(String visitor_auto_id, String visitor_gen_id, String visitor_visit_date) throws SQLException
//	{
//		this.objLogger.info("-:: VisitorCrudServiceImpl ::-" + " --> " + "getVisitorDetailByAutoNdGenIdNdVstDate()");
//		return this.objVisitorCrudRepo.getVisitorDetailByAutoNdGenIdNdVstDate(visitor_auto_id, visitor_gen_id, visitor_visit_date);
//	}
//
//	@Override
//	public String saveFrroVisitor(VisitorAddNewReq objVisitorAddNewReq) throws SQLException
//	{
//		this.objLogger.info("-:: VisitorCrudServiceImpl ::-" + " --> " + "saveFrroVisitor()");
//		return this.objVisitorCrudRepo.saveFrroVisitor(objVisitorAddNewReq);
//	}
//}
//
