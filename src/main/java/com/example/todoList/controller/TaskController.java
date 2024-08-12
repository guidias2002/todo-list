package com.example.todoList.controller;

import com.example.todoList.domain.Task;
import com.example.todoList.dto.RequestTaskDto;
import com.example.todoList.dto.ResponseTaskDto;
import com.example.todoList.dto.UpdateTaskDto;
import com.example.todoList.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    public ResponseEntity deleteTaskById(@PathVariable UUID id) {
        this.taskService.deleteTaskById(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity taskCompleted(@PathVariable UUID id) {
        this.taskService.taskCompleted(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/complete")
    public ResponseEntity<List<ResponseTaskDto>> listTaskComplete(){
        List<ResponseTaskDto> accomplishedTrue = this.taskService.listTaskByCompletionStatus(true);

        return ResponseEntity.ok(accomplishedTrue);
    }

    @GetMapping("/incomplete")
    public ResponseEntity<List<ResponseTaskDto>> listTaskIncomplete(){
        List<ResponseTaskDto> accomplishedFalse = this.taskService.listTaskByCompletionStatus(false);

        return ResponseEntity.ok(accomplishedFalse);
    }
}
