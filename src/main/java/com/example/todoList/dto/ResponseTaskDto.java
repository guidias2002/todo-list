package com.example.todoList.dto;

import java.util.UUID;

public record ResponseTaskDto(UUID id, String name, String description, Boolean accomplished, Integer priority) {
}
