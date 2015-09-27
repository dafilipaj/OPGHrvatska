package com.portal.opghrvatska.data;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1238467839846313930L;
	
	private int id;
	private int idcuser;
	private int idcthread;
	private String content;
	private Date date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdcuser() {
		return idcuser;
	}
	public void setIdcuser(int idcuser) {
		this.idcuser = idcuser;
	}
	public int getIdcthread() {
		return idcthread;
	}
	public void setIdcthread(int idcthread) {
		this.idcthread = idcthread;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
