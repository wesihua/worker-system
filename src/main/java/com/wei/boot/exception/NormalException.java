package com.wei.boot.exception;

public class NormalException extends Exception {

	private static final long serialVersionUID = 7369281203529868115L;
	
	private int code;
	
	private String message;
	
	public NormalException(int code, String message) {
		super(message);
		this.code = code;
	}

	public NormalException(String message) {
		super(message);
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
