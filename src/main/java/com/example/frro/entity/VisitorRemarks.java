package com.example.frro.entity;

public class VisitorRemarks {
	public String getVisitor_name() {
		return visitor_name;
	}
	public void setVisitor_name(String visitor_name) {
		this.visitor_name = visitor_name;
	}
	public String getVisitor_ppt() {
		return applicant_ppt_no;
	}
	public void setVisitor_ppt(String visitor_ppt) {
		this.applicant_ppt_no = visitor_ppt;
	}
	public String getVisitor_remarks() {
		return visitor_remarks;
	}
	public void setVisitor_remarks(String visitor_remarks) {
		this.visitor_remarks = visitor_remarks;
	}
	public String getSolver_name() {
		return solver_name;
	}
	public void setSolver_name(String solver_name) {
		this.solver_name = solver_name;
	}
	private String visitor_name;
	private String applicant_ppt_no;
	private String visitor_remarks;
	private String solver_name;
	
	public String getVisitor_genId() {
		return visitor_genId;
	}
	public void setVisitor_genId(String visitor_genId) {
		this.visitor_genId = visitor_genId;
	}
	private String visitor_genId;

	
}
