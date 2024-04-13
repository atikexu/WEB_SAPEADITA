package com.sapeadita.bean;

import java.io.Serializable;

public class ResponseWhatsappBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String responseDbSave;
	private String responseExSave;
	public String getResponseDbSave() {
		return responseDbSave;
	}
	public void setResponseDbSave(String responseDbSave) {
		this.responseDbSave = responseDbSave;
	}
	public String getResponseExSave() {
		return responseExSave;
	}
	public void setResponseExSave(String responseExSave) {
		this.responseExSave = responseExSave;
	}


}
