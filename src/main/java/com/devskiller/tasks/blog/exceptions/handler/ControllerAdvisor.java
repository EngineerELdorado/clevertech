package com.devskiller.tasks.blog.exceptions.handler;

import com.devskiller.tasks.blog.exceptions.CleverTech404Exception;
import com.devskiller.tasks.blog.exceptions.CleverTech4xxException;
import com.devskiller.tasks.blog.exceptions.models.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CleverTech404Exception.class)
	public ResponseEntity<Object> handleAccountNotFoundException(CleverTech4xxException exception) {

		ApiError apiError = new ApiError();
		apiError.setTimestamp(LocalDateTime.now());
		apiError.setMessage(exception.getMessage());
		apiError.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
	}

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatusCode(HttpStatus.BAD_REQUEST.value());
        StringBuilder message = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {

            String errorMessage = error.getDefaultMessage();
            message.append(errorMessage).append(". ");
        });
        apiError.setMessage(message.toString());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}

