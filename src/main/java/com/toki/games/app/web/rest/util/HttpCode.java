package com.toki.games.app.web.rest.util;

import com.fasterxml.jackson.annotation.JsonFormat;

public enum HttpCode {
	SUCCESS(200);

	private Integer value;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	private HttpCode(Integer value) {
		this.value = value;
	}

}
