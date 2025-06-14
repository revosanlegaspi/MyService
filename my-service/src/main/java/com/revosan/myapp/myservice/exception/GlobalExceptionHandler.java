/**
 * Project: MyService
 * Package: com.revosan.myapp.myservice.exception
 * File: GlobalExceptionHandler.java
 *
 * Description:
 * This class provides a centralized exception handling mechanism for the entire application.
 * Using `@ControllerAdvice`, it intercepts exceptions thrown by controllers and
 * `@ExceptionHandler` methods map specific exception types to appropriate HTTP responses.
 * This ensures consistent error reporting, especially for validation failures and
 * other common API errors, improving the developer experience for API consumers.
 *
 * Author: Revosan A. Legaspi
 * Date: June 14, 2025
 * Version: 1.0.0
 *
 * Change Log:
 * V1.0.0 - Initial creation of the GlobalExceptionHandler.
 */
package com.revosan.myapp.myservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles validation errors (e.g., from @Valid annotation on @RequestBody).
	 * Returns a 400 Bad Request with details about which fields failed validation.
	 * 
	 * @param ex MethodArgumentNotValidException thrown when @Valid fails.
	 * @return ResponseEntity with error details.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		Map<String, Object> response = new HashMap<>();
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("error", "Bad Request");
		response.put("message", "Validation failed");
		response.put("details", errors);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles generic unexpected exceptions. Returns a 500 Internal Server Error.
	 * 
	 * @param ex The exception that was thrown.
	 * @return ResponseEntity with generic error message.
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.put("error", "Internal Server Error");
		response.put("message", "An unexpected error occurred: " + ex.getMessage());

		// In production, avoid exposing detailed exception messages
		// Consider logging the full stack trace here and returning a more generic
		// message
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// You can add more specific @ExceptionHandler methods for other custom
	// exceptions
	// For example:
	// @ExceptionHandler(ResourceNotFoundException.class)
	// @ResponseStatus(HttpStatus.NOT_FOUND)
	// public ResponseEntity<Map<String, Object>>
	// handleResourceNotFoundException(ResourceNotFoundException ex) {
	// Map<String, Object> response = new HashMap<>();
	// response.put("timestamp", LocalDateTime.now());
	// response.put("status", HttpStatus.NOT_FOUND.value());
	// response.put("error", "Not Found");
	// response.put("message", ex.getMessage());
	// return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	// }
}