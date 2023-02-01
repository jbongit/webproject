package com.project.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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

	@Override
	public String toString() {
		return "Registration [id=" + id + ", name=" + name + ", emailid=" + emailid + ", password=" + password
				+ ", mobileno=" + mobileno + ", imageURL=" + imageURL + ", role=" + role + "]";
	}

}