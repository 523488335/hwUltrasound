package com.hw.bean;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable {

	private static final long serialVersionUID = 1598337595992111592L;

	private int currPageNum;
	private int maxPageNum;
	private int currPageSize;
	private Long maxPageSize;
	private List<T> list;
	public int getCurrPageNum() {
		return currPageNum;
	}
	public void setCurrPageNum(int currPageNum) {
		this.currPageNum = currPageNum;
	}
	public int getMaxPageNum() {
		return maxPageNum;
	}
	public void setMaxPageNum(int maxPageNum) {
		this.maxPageNum = maxPageNum;
	}
	public Long getMaxPageSize() {
		return maxPageSize;
	}
	public void setMaxPageSize(Long maxPageSize) {
		this.maxPageSize = maxPageSize;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getCurrPageSize() {
		return currPageSize;
	}
	public void setCurrPageSize(int currPageSize) {
		this.currPageSize = currPageSize;
	}
	@Override
	public String toString() {
		return "PageBean [currPageNum=" + currPageNum + ", maxPageNum=" + maxPageNum + ", currPageSize=" + currPageSize
				+ ", maxPageSize=" + maxPageSize + ", list=" + list + "]";
	}
}
