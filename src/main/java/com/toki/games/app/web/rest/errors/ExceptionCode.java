package com.toki.games.app.web.rest.errors;

public enum ExceptionCode {

	NOT_FOUND("1001","Not found"),;
	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	private ExceptionCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

}
