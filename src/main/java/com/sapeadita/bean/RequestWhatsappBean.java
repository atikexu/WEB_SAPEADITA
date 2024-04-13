package com.sapeadita.bean;

import java.io.Serializable;

public class RequestWhatsappBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String message;
	private String phone;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}


}
