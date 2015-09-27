package com.portal.opghrvatska.data;

import java.io.Serializable;
import java.util.Date;

public class Ad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4256263574248563739L;

	private int id;
	private int opg;
	private int user;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOpg() {
		return opg;
	}
	public void setOpg(int opg) {
		this.opg = opg;
	}
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
