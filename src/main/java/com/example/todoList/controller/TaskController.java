package com.example.todoList.controller;

import com.example.todoList.dto.RequestTaskDto;
import com.example.todoList.dto.ResponseTaskDto;
import com.example.todoList.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity createTask(@RequestBody @Valid RequestTaskDto data){
        this.taskService.createTask(data);

        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseTaskDto>> listTasks(){
        List<ResponseTaskDto> listTasks = this.taskService.listTasks();

        return ResponseEntity.ok(listTasks);
    }
}
