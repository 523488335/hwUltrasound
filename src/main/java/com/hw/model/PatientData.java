package com.hw.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "patient_data", catalog = "hw_ultrasonic_data_jpa")
public class PatientData implements Serializable{
	
	private static final long serialVersionUID = 5232230492820239984L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientDataId;
	
	@Column
    private Integer patientId;

	@Column
    private String description;

	@Column
    private String principal;

	@Column
    private String history;

	@Column
    private Integer userId;

	@Column
    private String status;

	@Column
    private String video;

	@Column
    private String report;

	@Column
    private String source;

	@Column
    private String type;

	@Column
    private String date;

	@Column
    private Integer age;

    public Integer getPatientDataId() {
		return patientDataId;
	}

	public void setPatientDataId(Integer patientDataId) {
		this.patientDataId = patientDataId;
	}

	public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal == null ? null : principal.trim();
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history == null ? null : history.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video == null ? null : video.trim();
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report == null ? null : report.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}