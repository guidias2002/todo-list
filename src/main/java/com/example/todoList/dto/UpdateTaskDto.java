package com.example.todoList.dto;

public record UpdateTaskDto(
        String name,
        String description,
        Integer priority) {
}
