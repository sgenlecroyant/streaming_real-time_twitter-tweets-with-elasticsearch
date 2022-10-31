package com.sgenlecroyant.twitter.model;

import java.util.Date;

public class Tweet {

	private String id;
//	private Date created_at;
	private String text;

	public Tweet() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public Date getCreated_at() {
//		return created_at;
//	}
//
//	public void setCreated_at(Date created_at) {
//		this.created_at = created_at;
//	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Tweet [id=" + id + ", text=" + text + "]";
	}

}
