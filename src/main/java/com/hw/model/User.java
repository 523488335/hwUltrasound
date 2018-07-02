package com.hw.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user", catalog = "hw_ultrasonic_data_jpa")
public class User implements Serializable{
	
	private static final long serialVersionUID = -8962744204773591545L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	@Column
    private String name;

	@Column
    private String username;

	@Column
    private String password;
	
	@Column
    private String idNumber;
	
	@Column
    private String identity;
	
	@Column
    private String endLanded;

	@Column
    private String endLocation;
	
	@Column
    private Integer count;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column
    private String isLicense;
	
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
        this.name = name == null ? null : name.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIsLicense() {
		return isLicense;
	}

	public void setIsLicense(String isLicense) {
		this.isLicense = isLicense;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEndLanded() {
		return endLanded;
	}

	public void setEndLanded(String endLanded) {
		this.endLanded = endLanded;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}