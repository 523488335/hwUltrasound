package com.hw.bean;

public class Point {

	private String x;
	private String y;
	private String z;
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getZ() {
		return z;
	}
	public void setZ(String z) {
		this.z = z;
	}
	public Point(String x, String y, String z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
