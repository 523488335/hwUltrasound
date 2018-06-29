package com.hw.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private String birthday;

	@Column
    private String recentlyLanded;

	@Column
    private String recentlyLocation;

	@Column
    private String endLanded;

	@Column
    private String endLocation;

	@Column
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getRecentlyLanded() {
        return recentlyLanded;
    }

    public void setRecentlyLanded(String recentlyLanded) {
        this.recentlyLanded = recentlyLanded == null ? null : recentlyLanded.trim();
    }

    public String getRecentlyLocation() {
        return recentlyLocation;
    }

    public void setRecentlyLocation(String recentlyLocation) {
        this.recentlyLocation = recentlyLocation == null ? null : recentlyLocation.trim();
    }

    public String getEndLanded() {
        return endLanded;
    }

    public void setEndLanded(String endLanded) {
        this.endLanded = endLanded == null ? null : endLanded.trim();
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation == null ? null : endLocation.trim();
    }
}