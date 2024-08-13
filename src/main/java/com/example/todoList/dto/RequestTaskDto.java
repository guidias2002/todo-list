package com.example.todoList.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestTaskDto(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Priority is required")
        @Min(value = 1, message = "Priority must be between 1 and 5")
        @Max(value = 5, message = "Priority must be between 1 and 5")
        Integer priority) {
}
