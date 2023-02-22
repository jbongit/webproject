package com.project.model;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "REGISTRATION_INFO")

public class Registration {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message="Name Can Not Be Empty !!")
	private String name;

	@Column(unique = true)
	@Email(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z_.-]+$",message="Must Be in Form of 'user@example.com'")
	@NotBlank(message="Email Can Not Be Empty !!")
	private String emailid;
	
	@NotBlank(message="Password Can Not Be Empty !!")
	private String password;

	@NotBlank(message="Mobile Number Can Not Be Empty !!")
	@Pattern(regexp="^[0-9]{10}$",message="Mobile Number must consists of 10 Digits")
	private String mobileno;

	private String imageURL;
	
	@Column(nullable=false ,columnDefinition = "varchar(255) default 'ROLE_USER'")
	private String role;

	@Column(nullable=false ,columnDefinition = "varchar(255) default 'PENDING'")
	private String status;
	
	private String approverName;
	
	private LocalDateTime RegistrationdateTime;
	
	private String OTP;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public LocalDateTime getRegistrationdateTime() {
		return RegistrationdateTime;
	}

	public void setRegistrationdateTime(LocalDateTime registrationdateTime) {
		RegistrationdateTime = registrationdateTime;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	@Override
	public String toString() {
		return "Registration [id=" + id + ", name=" + name + ", emailid=" + emailid + ", password=" + password
				+ ", mobileno=" + mobileno + ", imageURL=" + imageURL + ", role=" + role + ", status=" + status
				+ ", approverName=" + approverName + ", RegistrationdateTime=" + RegistrationdateTime + ", OTP=" + OTP
				+ "]";
	}

}