package com.example.todoList.controller;

import com.example.todoList.domain.Task;
import com.example.todoList.dto.RequestTaskDto;
import com.example.todoList.dto.ResponseTaskDto;
import com.example.todoList.dto.UpdateTaskDto;
import com.example.todoList.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/todos")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity createTask(@RequestBody @Valid RequestTaskDto data) {
        this.taskService.createTask(data);

        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseTaskDto>> listTasks() {
        List<ResponseTaskDto> listTasks = this.taskService.listTasks();

        return ResponseEntity.ok(listTasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTask(@PathVariable UUID id, @RequestBody @Valid UpdateTaskDto data) {
        this.taskService.updateTask(id, data);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskByid(@PathVariable UUID id) {
        Optional<Task> task = this.taskService.getTaskById(id);

        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());


    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable UUID id){
        this.taskService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
