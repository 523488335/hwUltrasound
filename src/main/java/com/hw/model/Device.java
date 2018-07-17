package com.hw.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "devices", catalog = "hw_ultrasonic_data_jpa")
public class Device {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer deviceId;
	
	@NonNull
	@Column
	private String deviceNo;
	
	@Column
	private String local;
	
	@Column
	private Date date;
}
