package com.project.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "APPROVER_INFO")
public class Approver {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String approvername;
	private String approveremailid;
	private LocalDateTime dateTime;
	private String username;
	private String useremailid;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApprovername() {
		return approvername;
	}
	public void setApprovername(String approvername) {
		this.approvername = approvername;
	}
	public String getApproveremailid() {
		return approveremailid;
	}
	public void setApproveremailid(String approveremailid) {
		this.approveremailid = approveremailid;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUseremailid() {
		return useremailid;
	}
	public void setUseremailid(String useremailid) {
		this.useremailid = useremailid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Approver [id=" + id + ", approvername=" + approvername + ", approveremailid=" + approveremailid
				+ ", dateTime=" + dateTime + ", username=" + username + ", useremailid=" + useremailid + ", status="
				+ status + "]";
	}

}
