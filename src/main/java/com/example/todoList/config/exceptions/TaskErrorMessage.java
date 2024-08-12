package com.example.todoList.config.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TaskErrorMessage {
    private final String status;
    private final Map<String, String> errors;

    public TaskErrorMessage(HttpStatus status, Map<String, String> errors) {
        this.status = status.toString();
        this.errors = errors;
    }
}
