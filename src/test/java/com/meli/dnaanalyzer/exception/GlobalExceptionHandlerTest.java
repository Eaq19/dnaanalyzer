package com.meli.dnaanalyzer.exception;

import com.meli.dnaanalyzer.model.APIError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GlobalExceptionHandlerTest {

    @Mock
    private WebRequest request;
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void handleArrayIndexException() {
        //Given
        Exception ex = new ArrayIndexException("Prueba");
        //When
        ResponseEntity<APIError> response = globalExceptionHandler.handleException(ex, request);
        //Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getErrors()).isNotNull();
        assertThat(response.getBody().getErrors().get(0)).isEqualTo("Prueba");
    }

    @Test
    public void handleContentNotAllowedExceptionException() {
        //Given
        List<ObjectError> errors = Arrays.asList(new ObjectError("Error", "object Error"));
        Exception ex = ContentNotAllowedException.createWith(errors);
        //When
        ResponseEntity<APIError> response = globalExceptionHandler.handleException(ex, request);
        //Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getErrors()).isNotNull();
        assertThat(response.getBody().getErrors().get(0)).isEqualTo("Error object Error");
    }

    @Test
    public void handleException() {
        //Given
        Exception ex = new Exception("Prueba");
        //When
        ResponseEntity<APIError> response = globalExceptionHandler.handleException(ex, request);
        //Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}