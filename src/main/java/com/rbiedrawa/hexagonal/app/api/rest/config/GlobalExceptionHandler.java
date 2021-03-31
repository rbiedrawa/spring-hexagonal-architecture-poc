package com.rbiedrawa.hexagonal.app.api.rest.config;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rbiedrawa.hexagonal.app.business.common.exceptions.ApplicationException;
import com.rbiedrawa.hexagonal.app.business.common.exceptions.NotFoundException;
import com.rbiedrawa.hexagonal.app.business.orders.OrderValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = OrderValidationException.class)
	public void handleOrderValidationException(HttpServletResponse response, OrderValidationException ex) throws IOException {
		log.error("Order validation failure: ", ex);
		response.sendError(HttpStatus.CONFLICT.value());
	}

	@ExceptionHandler(value = NotFoundException.class)
	public void handleNotFoundException(HttpServletResponse response, NotFoundException ex) throws IOException {
		log.error("Entity not found: ", ex);
		response.sendError(HttpStatus.NOT_FOUND.value());
	}

	@ExceptionHandler(value = ApplicationException.class)
	public void handleApplicationException(HttpServletResponse response, ApplicationException ex) throws IOException {
		log.error("Application exception: ", ex);
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@ExceptionHandler(value = Exception.class)
	public void handleException(HttpServletResponse response, Exception ex) throws IOException {
		log.error("General exception: ", ex);
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
}
