package com.avenuecode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(GraphNotFoundException.class)
	public final ResponseEntity<GraphNotFoundExceptionDetails> handleUserNotFoundException(GraphNotFoundExceptionDetails ex, WebRequest request) {
		return new ResponseEntity<GraphNotFoundExceptionDetails>(new GraphNotFoundExceptionDetails(), HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(PathNotFoundException.class)
	public final ResponseEntity<PathNotFoundExceptionDetails> handleUserNotFoundException(PathNotFoundExceptionDetails ex, WebRequest request) {
		return new ResponseEntity<PathNotFoundExceptionDetails>(new PathNotFoundExceptionDetails(), HttpStatus.NOT_FOUND);
	}
	
}
