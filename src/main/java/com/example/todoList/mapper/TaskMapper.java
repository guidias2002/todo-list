package com.example.todoList.mapper;


import com.example.todoList.domain.Task;
import com.example.todoList.dto.ResponseTaskDto;

public class TaskMapper {
    public static ResponseTaskDto toDto(Task task){
        return new ResponseTaskDto(task.getId(), task.getName(), task.getDescription(), task.getAccomplished(), task.getPriority());
    }
}
