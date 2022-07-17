package com.meli.dnaanalyzer.exception;

import com.meli.dnaanalyzer.model.APIError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Provides handling for exceptions throughout this service.
     */
    @ExceptionHandler({ContentNotAllowedException.class, ArrayIndexException.class, Exception.class})
    public final ResponseEntity<APIError> handleException(Exception ex, WebRequest request) {
        ResponseEntity<APIError> response = null;
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof ContentNotAllowedException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            ContentNotAllowedException cnae = (ContentNotAllowedException) ex;

            response = handleContentNotAllowedException(cnae, headers, status, request);
        } else if (ex instanceof ArrayIndexException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            ArrayIndexException cnae = (ArrayIndexException) ex;

            response = handleExceptionInternal(ex, new APIError(Arrays.asList(cnae.getMessage())), headers, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            response = handleExceptionInternal(ex, null, headers, status, request);
        }
        return response;
    }

    /**
     * Customize the response for ContentNotAllowedException.
     */
    private ResponseEntity<APIError> handleContentNotAllowedException(ContentNotAllowedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessages = ex.getErrors().stream().map(contentError -> contentError.getObjectName() + " " + contentError.getDefaultMessage()).collect(Collectors.toList());
        return handleExceptionInternal(ex, new APIError(errorMessages), headers, status, request);
    }

    /**
     * A single place to customize the response body of all Exception types.
     */
    private ResponseEntity<APIError> handleExceptionInternal(Exception ex, APIError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, RequestAttributes.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }
}
