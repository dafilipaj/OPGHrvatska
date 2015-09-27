package com.portal.opghrvatska.data;

import java.io.Serializable;
import java.util.Date;

public class ForumThread implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1313675106394911226L;

	private int id;
	private int idtuser;
	private String title;
	private String content;
	private Date date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdtuser() {
		return idtuser;
	}
	public void setIdtuser(int idtuser) {
		this.idtuser = idtuser;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
