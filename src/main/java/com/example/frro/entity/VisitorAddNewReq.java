/*
 * package com.example.frro.entity;
 * 
 * import jakarta.validation.constraints.NotBlank; import
 * jakarta.validation.constraints.NotEmpty; import
 * jakarta.validation.constraints.NotNull; import
 * jakarta.validation.constraints.Pattern; import
 * jakarta.validation.constraints.Size;
 * 
 * public class VisitorAddNewReq { private String visitor_frro_fro_code;
 * 
 * @NotBlank(message = "cannot be blank")
 * 
 * @Size(min = 3, max = 200, message = "minimum 3 and max of 200 chars.")
 * 
 * @Pattern(regexp = "^(?!^0+$)[A-Z. ]+$", message = "invalid format") private
 * String visitor_full_name;
 * 
 * @Size(max = 2, message = "invalid format.")
 * 
 * @Pattern(regexp = "^[0-9]{1,2}$", message = "invalid format") private String
 * visitor_id_type_id;
 * 
 * @Size(min = 3, max = 50, message = "invalid format.")
 * 
 * @Pattern(regexp = "^[A-Z0-9.+ -/:()]{3,50}$", message = "invalid format")
 * private String visitor_id_type;
 * 
 * @NotNull(message = "Address, is required")
 * 
 * @NotEmpty(message = "Address, cannot be empty")
 * 
 * @NotBlank(message = "Address, cannot be blank")
 * 
 * @Size(min = 20, max = 200, message =
 * "Address, minimum 20 and max of 200 chars.")
 * 
 * @Pattern(regexp = "^[A-Z0-9.,/ ]+$", message = "Address, invalid input")
 * private String visitor_address;
 * 
 * @NotNull(message = "Visitor name, is required")
 * 
 * @NotEmpty(message = "Visitor name, cannot be empty")
 * 
 * @NotBlank(message = "Visitor name, cannot be blank")
 * 
 * @Size(min = 3, max = 200, message =
 * "Visitor name, minimum 3 and max of 200 chars.")
 * 
 * @Pattern(regexp = "^(?!^0+$)[A-Z. ]+$", message = "FRRO code, invalid input")
 * private String applicant_full_name;
 * 
 * @NotNull(message = "Passport No., is required")
 * 
 * @NotEmpty(message = "Passport No., cannot be empty")
 * 
 * @NotBlank(message = "Passport No., cannot be blank")
 * 
 * @Size(min = 3, max = 20, message =
 * "Passport No., minimum 3 and max of 20 chars.")
 * 
 * @Pattern(regexp = "^[A-Z0-9/()-]{3,20}$", message =
 * "Passport No., invalid input") private String applicant_ppt_no;
 * 
 * @NotNull(message = "Nationality, is required")
 * 
 * @NotEmpty(message = "Nationality, cannot be empty")
 * 
 * @NotBlank(message = "Nationality, cannot be blank")
 * 
 * @Size(min = 3, max = 3, message = "Nationality, 3 chars.")
 * 
 * @Pattern(regexp = "^[A-Z]{3}$", message = "Nationality, invalid input")
 * private String applicant_nationality;
 * 
 * @NotNull(message = "Contact No., is required")
 * 
 * @NotEmpty(message = "Contact No., cannot be empty")
 * 
 * @NotBlank(message = "Contact No., cannot be blank")
 * 
 * @Size(min = 10, max = 12, message =
 * "Contact No., minimum 10 and max of 12 digits.")
 * 
 * @Pattern(regexp = "^[0-9]{10,12}$", message = "Contact No., invalid input")
 * private String visitor_contact_no_india;
 * 
 * @Size(min = 3, max = 3, message = "Nationality, 3 chars.")
 * 
 * @Pattern(regexp = "^[A-Z]{3}$", message = "Nationality, invalid input")
 * private String visitor_country_code_num;
 * 
 * @Size(min = 10, max = 15, message =
 * "Contact No. I, minimum 10 and max of 15 digits.")
 * 
 * @Pattern(regexp = "^[0-9]{10,15}$", message = "Contact No. I, invalid input")
 * private String visitor_contact_no_intnl;
 * 
 * @NotNull(message = "Purpose Id, is required")
 * 
 * @NotEmpty(message = "Purpose Id, cannot be empty")
 * 
 * @NotBlank(message = "Purpose Id, cannot be blank")
 * 
 * @Size(min = 1, max = 2, message = "Purpose Id, invalid format.")
 * 
 * @Pattern(regexp = "^[0-9]{1,2}$", message = "Purpose Id, invalid input")
 * private String visitor_visit_purpose_id;
 * 
 * @Size(min = 3, max = 50, message = "Purpose, invalid format.")
 * 
 * @Pattern(regexp = "^[A-Z.+-/:()\\\\s ]{3,50}$", message =
 * "Purpose, invalid input") private String visit_purpose_other_desc;
 * 
 * public VisitorAddNewReq() { this.visitor_frro_fro_code = null;
 * this.visitor_full_name = null; this.visitor_id_type_id = null;
 * this.visitor_id_type = null; this.visitor_address = null;
 * this.applicant_full_name = null; this.applicant_ppt_no = null;
 * this.applicant_nationality = null; this.visitor_contact_no_india = null;
 * this.visitor_country_code_num = null; this.visitor_contact_no_intnl = null;
 * this.visitor_visit_purpose_id = null; this.visit_purpose_other_desc = null; }
 * 
 * public String getApplicant_full_name() { return this.applicant_full_name; }
 * 
 * public String getApplicant_nationality() { return this.applicant_nationality;
 * }
 * 
 * public String getApplicant_ppt_no() { return this.applicant_ppt_no; }
 * 
 * public String getVisit_purpose_other_desc() { return
 * this.visit_purpose_other_desc; }
 * 
 * public String getVisitor_address() { return this.visitor_address; }
 * 
 * public String getVisitor_contact_no_india() { return
 * this.visitor_contact_no_india; }
 * 
 * public String getVisitor_contact_no_intnl() { return
 * this.visitor_contact_no_intnl; }
 * 
 * public String getVisitor_country_code_num() { return
 * this.visitor_country_code_num; }
 * 
 * public String getVisitor_frro_fro_code() { return this.visitor_frro_fro_code;
 * }
 * 
 * public String getVisitor_full_name() { return this.visitor_full_name; }
 * 
 * public String getVisitor_id_type() { return this.visitor_id_type; }
 * 
 * public String getVisitor_id_type_id() { return this.visitor_id_type_id; }
 * 
 * public String getVisitor_visit_purpose_id() { return
 * this.visitor_visit_purpose_id; }
 * 
 * public void setApplicant_full_name(String applicant_full_name) {
 * this.applicant_full_name = applicant_full_name; }
 * 
 * public void setApplicant_nationality(String applicant_nationality) {
 * this.applicant_nationality = applicant_nationality; }
 * 
 * public void setApplicant_ppt_no(String applicant_ppt_no) {
 * this.applicant_ppt_no = applicant_ppt_no; }
 * 
 * public void setVisit_purpose_other_desc(String visit_purpose_other_desc) {
 * this.visit_purpose_other_desc = visit_purpose_other_desc; }
 * 
 * public void setVisitor_address(String visitor_address) { this.visitor_address
 * = visitor_address; }
 * 
 * public void setVisitor_contact_no_india(String visitor_contact_no_india) {
 * this.visitor_contact_no_india = visitor_contact_no_india; }
 * 
 * public void setVisitor_contact_no_intnl(String visitor_contact_no_intnl) {
 * this.visitor_contact_no_intnl = visitor_contact_no_intnl; }
 * 
 * public void setVisitor_country_code_num(String visitor_country_code_num) {
 * this.visitor_country_code_num = visitor_country_code_num; }
 * 
 * public void setVisitor_frro_fro_code(String visitor_frro_fro_code) {
 * this.visitor_frro_fro_code = visitor_frro_fro_code; }
 * 
 * public void setVisitor_full_name(String visitor_full_name) {
 * this.visitor_full_name = visitor_full_name; }
 * 
 * public void setVisitor_id_type(String visitor_id_type) { this.visitor_id_type
 * = visitor_id_type; }
 * 
 * public void setVisitor_id_type_id(String visitor_id_type_id) {
 * this.visitor_id_type_id = visitor_id_type_id; }
 * 
 * public void setVisitor_visit_purpose_id(String visitor_visit_purpose_id) {
 * this.visitor_visit_purpose_id = visitor_visit_purpose_id; } }
 */