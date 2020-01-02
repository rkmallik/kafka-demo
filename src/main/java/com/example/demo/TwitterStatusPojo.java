package com.example.demo;

import java.util.Date;

import twitter4j.Status;

public class TwitterStatusPojo {

	private Date createdAt;
	private long id;
	private String text;
	private String lang;
	private String screenname;
	
	public TwitterStatusPojo() {
		
	}
	
	public TwitterStatusPojo(Status status) {
		this.createdAt = status.getCreatedAt();
		this.id = status.getId();
		this.text = status.getText();
		this.lang = status.getLang();
		this.screenname = status.getUser().getScreenName();
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getScreenname() {
		return screenname;
	}
	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}
	
	public String toString() {
		return createdAt.toString()+" User: "+screenname+" Tweet text: "+text+" ID: "+id;
	}

}
