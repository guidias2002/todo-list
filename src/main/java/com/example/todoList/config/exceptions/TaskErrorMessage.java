package com.example.todoList.config.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class TaskErrorMessage {
    private final String status;
    private final List<String> errors;

    public TaskErrorMessage(HttpStatus status, List<String> errors) {
        this.status = status.toString();
        this.errors = errors;
    }
}
