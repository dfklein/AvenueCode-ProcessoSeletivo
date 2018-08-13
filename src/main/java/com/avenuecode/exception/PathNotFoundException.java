package com.avenuecode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@ResponseStatus(HttpStatus.NOT_FOUND)
@NoArgsConstructor
public class PathNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -6919777261843990673L;

	public PathNotFoundException(String msg) {
		super(msg);
	}

}
