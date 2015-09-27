package com.portal.opghrvatska.data;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5312704769142233384L;
	
	private int id;
	private int author;
	private String title;
	private String content;
	private Date date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAuthor() {
		return author;
	}
	public void setAuthor(int author) {
		this.author = author;
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
