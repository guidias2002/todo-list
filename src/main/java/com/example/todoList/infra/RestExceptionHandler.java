package com.example.todoList.infra;

import com.example.todoList.config.exceptions.TaskErrorMessage;
import com.example.todoList.config.exceptions.TaskNotFoundException;
import com.example.todoList.config.exceptions.ValidationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // Criação da lista de erros a partir dos detalhes da exceção
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage
                ));

        TaskErrorMessage taskErrorMessage = new TaskErrorMessage(HttpStatus.BAD_REQUEST, errors);

        return new ResponseEntity<>(taskErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessage = "Invalid input";

        Map<String, String> errors = new HashMap<>();

        Throwable cause = ex.getCause();
        if (cause instanceof JsonMappingException) {
            JsonMappingException jsonMappingException = (JsonMappingException) cause;

            String fieldName = jsonMappingException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));

            errorMessage = "Invalid value for failed" + fieldName;
            errors.put(fieldName, "Invalid value");
        } else {
            errors.put("error", errorMessage);
        }

        TaskErrorMessage taskErrorMessage = new TaskErrorMessage(HttpStatus.BAD_REQUEST, errors);

        return new ResponseEntity<>(taskErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    private ResponseEntity<TaskErrorMessage> handleTaskNotFoundException(TaskNotFoundException ex) {
        TaskErrorMessage taskErrorMessage = new TaskErrorMessage(HttpStatus.NOT_FOUND, Map.of("error", ex.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(taskErrorMessage);
    }

    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<Object> handleValidation(ValidationException ex){
        Map<String, String> errors = ex.getErrors();

        TaskErrorMessage taskErrorMessage = new TaskErrorMessage(HttpStatus.BAD_REQUEST, errors);

        return new ResponseEntity<>(taskErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<TaskErrorMessage> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Invalid request: " + ex.getValue());

        TaskErrorMessage errorResponse = new TaskErrorMessage(HttpStatus.BAD_REQUEST, errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
