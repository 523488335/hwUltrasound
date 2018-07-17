package com.hw.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image", catalog = "hw_ultrasonic_data_jpa")
public class Image implements Serializable{

	private static final long serialVersionUID = -3106286073747110060L;

	@Id
	@Column
	private Long imageId;
	
	@Column
	private Integer deviceId;
	
	@Column
	private Integer patientDataId;
	
	@Column
	private boolean isLeft;
	
	@Column
	private String path;
	
	@Column
	private short type;
	
	public short getType() {
		return type;
	}

	public Integer getDeviceId() {
		return deviceId;
	}



	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}



	public void setType(short type) {
		this.type = type;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public Integer getPatientDataId() {
		return patientDataId;
	}

	public void setPatientDataId(Integer patientDataId) {
		this.patientDataId = patientDataId;
	}

	public boolean isLeft() {
		return isLeft;
	}

	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Image [imageId=" + imageId + ", patientDataId=" + patientDataId + ", isLeft=" + isLeft + ", path="
				+ path + ", type=" + type + "]";
	}
	
}
