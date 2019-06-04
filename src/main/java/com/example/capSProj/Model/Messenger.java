package com.example.capSProj.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="messenger")
public class Messenger {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int mid;
	
	private String receiver;
	
	private String sender;
	
	private String typedContent;
	
	private String date;

	
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getTypedContent() {
		return typedContent;
	}

	public void setTypedContent(String typedContent) {
		this.typedContent = typedContent;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
