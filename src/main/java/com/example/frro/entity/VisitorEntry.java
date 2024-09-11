package com.example.frro.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;

public class VisitorEntry {

    private String visitor_frro_fro_code;

    @NotBlank(message = "cannot be blank")
    @Size(min = 3, max = 200, message = "minimum 3 and max of 200 chars.")
    @Pattern(regexp = "^(?!^0+$)[A-Z. ]+$", message = "invalid format")
    private String visitor_full_name;

    @NotNull(message = "Visitor ID type ID is required")
    @Min(value = 1, message = "ID type ID must be greater than or equal to 1")
    @Max(value = 99, message = "ID type ID must be less than or equal to 99")
    private Short visitor_id_type_id;

    @Size(min = 3, max = 50, message = "invalid format.")
    @Pattern(regexp = "^[A-Z0-9.+ -/:()]{3,50}$", message = "invalid format")
    private String visitor_id_type;

    @NotBlank(message = "Address cannot be blank")
    @Size(min = 20, max = 200, message = "Address must be between 20 and 200 characters.")
    @Pattern(regexp = "^[A-Z0-9.,/ ]+$", message = "Address invalid input")
    private String visitor_address;

    @NotBlank(message = "Visitor name cannot be blank")
    @Size(min = 3, max = 200, message = "Visitor name must be between 3 and 200 characters.")
    @Pattern(regexp = "^(?!^0+$)[A-Z. ]+$", message = "Visitor name invalid input")
    private String applicant_full_name;

    @NotBlank(message = "Passport No. cannot be blank")
    @Size(min = 3, max = 20, message = "Passport No. must be between 3 and 20 characters.")
    @Pattern(regexp = "^[A-Z0-9/()-]{3,20}$", message = "Passport No. invalid input")
    private String applicant_ppt_no;

    @NotBlank(message = "Nationality cannot be blank")
    @Size(min = 3, max = 3, message = "Nationality must be exactly 3 characters.")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Nationality invalid input")
    private String applicant_nationality;

    @NotBlank(message = "Contact No. cannot be blank")
    @Size(min = 10, max = 12, message = "Contact No. must be between 10 and 12 digits.")
    @Pattern(regexp = "^[0-9]{10,12}$", message = "Contact No. invalid input")
    private String visitor_contact_no_india;

    @Size(min = 3, max = 3, message = "Country code must be exactly 3 characters.")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Country code invalid input")
    private String visitor_country_code_num;

    @Size(min = 10, max = 15, message = "Contact No. must be between 10 and 15 digits.")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Contact No. invalid input")
    private String visitor_contact_no_intnl;

    @NotNull(message = "Purpose Id is required")
    @Min(value = 1, message = "Purpose Id must be greater than or equal to 1")
    @Max(value = 99, message = "Purpose Id must be less than or equal to 99")
    private Short visitor_visit_purpose_id;

    @Size(min = 3, max = 50, message = "Purpose description must be between 3 and 50 characters.")
    @Pattern(regexp = "^[A-Z.+-/:()\\s ]{3,50}$", message = "Purpose description invalid input")
    private String visitor_purpose_other_desc;

    @Lob
    private byte[] visitor_img;

    private String imageBase64;

    private String genId;
    private Short dailyToken;
    private String frroCode;
    private Timestamp timestamp;

    // Getters and Setters
    public String getVisitor_frro_fro_code() {
        return visitor_frro_fro_code;
    }

    public void setVisitor_frro_fro_code(String visitor_frro_fro_code) {
        this.visitor_frro_fro_code = visitor_frro_fro_code;
    }

    public String getVisitor_full_name() {
        return visitor_full_name;
    }

    public void setVisitor_full_name(String visitor_full_name) {
        this.visitor_full_name = visitor_full_name;
    }

    public Short getVisitor_id_type_id() {
        return visitor_id_type_id;
    }

    public void setVisitor_id_type_id(Short visitor_id_type_id) {
        this.visitor_id_type_id = visitor_id_type_id;
    }

    public String getVisitor_id_type() {
        return visitor_id_type;
    }

    public void setVisitor_id_type(String visitor_id_type) {
        this.visitor_id_type = visitor_id_type;
    }

    public String getVisitor_address() {
        return visitor_address;
    }

    public void setVisitor_address(String visitor_address) {
        this.visitor_address = visitor_address;
    }

    public String getApplicant_full_name() {
        return applicant_full_name;
    }

    public void setApplicant_full_name(String applicant_full_name) {
        this.applicant_full_name = applicant_full_name;
    }

    public String getApplicant_ppt_no() {
        return applicant_ppt_no;
    }

    public void setApplicant_ppt_no(String applicant_ppt_no) {
        this.applicant_ppt_no = applicant_ppt_no;
    }

    public String getApplicant_nationality() {
        return applicant_nationality;
    }

    public void setApplicant_nationality(String applicant_nationality) {
        this.applicant_nationality = applicant_nationality;
    }

    public String getVisitor_contact_no_india() {
        return visitor_contact_no_india;
    }

    public void setVisitor_contact_no_india(String visitor_contact_no_india) {
        this.visitor_contact_no_india = visitor_contact_no_india;
    }

    public String getVisitor_country_code_num() {
        return visitor_country_code_num;
    }

    public void setVisitor_country_code_num(String visitor_country_code_num) {
        this.visitor_country_code_num = visitor_country_code_num;
    }

    public String getVisitor_contact_no_intnl() {
        return visitor_contact_no_intnl;
    }

    public void setVisitor_contact_no_intnl(String visitor_contact_no_intnl) {
        this.visitor_contact_no_intnl = visitor_contact_no_intnl;
    }

    public Short getVisitor_visit_purpose_id() {
        return visitor_visit_purpose_id;
    }

    public void setVisitor_visit_purpose_id(Short visitor_visit_purpose_id) {
        this.visitor_visit_purpose_id = visitor_visit_purpose_id;
    }

    public String getVisit_purpose_other_desc() {
        return visitor_purpose_other_desc;
    }

    public void setVisit_purpose_other_desc(String visit_purpose_other_desc) {
        this.visitor_purpose_other_desc = visit_purpose_other_desc;
    }

    public byte[] getVisitor_img() {
        return visitor_img;
    }

    public void setVisitor_img(byte[] visitor_img) {
        this.visitor_img = visitor_img;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getGenId() {
        return genId;
    }

    public void setGenId(String genId) {
        this.genId = genId;
    }

    public Short getDailyToken() {
        return dailyToken;
    }

    public void setDailyToken(Short dailyToken) {
        this.dailyToken = dailyToken;
    }

    public String getFrroCode() {
        return frroCode;
    }

    public void setFrroCode(String frroCode) {
        this.frroCode = frroCode;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedTimestamp() {
        if (timestamp != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            return formatter.format(timestamp);
        }
        return null; // or return an empty string or a default value if timestamp is null
    }

	public void setStatus(String status) {
     this.status = 		status;
	}
	public String getStatus() {
		return status;
	}
	private String status;
    public String getSolver_name() {
		return solver_name;
	}

	public void setSolver_name(String solver_name) {
		this.solver_name = solver_name;
	}
	private String solver_name;
	public String getVisitor_remarks() {
		return visitor_remarks;
	}

	public void setVisitor_remarks(String visitor_remarks) {
		this.visitor_remarks = visitor_remarks;
	}
	private String visitor_remarks;
	
}
