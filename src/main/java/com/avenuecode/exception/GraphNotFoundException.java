package com.avenuecode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GraphNotFoundException extends RuntimeException {
	

	private static final long serialVersionUID = 7145521216691672701L;

	public GraphNotFoundException(String msg) {
		super(msg);
	}
	
}
