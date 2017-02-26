package com.luna.utils.enm;

public enum VerificationEnum {

	MD5("MD5"), 
	SHA("SHA")
	;
	private String name;

	private VerificationEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}