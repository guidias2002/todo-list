package com.example.todoList.controller;

import com.example.todoList.domain.Task;
import com.example.todoList.dto.RequestTaskDto;
import com.example.todoList.dto.ResponseTaskDto;
import com.example.todoList.dto.UpdateTaskDto;
import com.example.todoList.mapper.TaskMapper;
import com.example.todoList.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(description = "Cria a tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cria a tarefa"),
            @ApiResponse(responseCode = "400", description = "A tarefa não foi criada")
    })
    @PostMapping
    public ResponseEntity createTask(@RequestBody @Valid RequestTaskDto data) {
        this.taskService.createTask(data);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(description = "Retorna as tarefas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as tarefa")
    })
    @GetMapping
    public ResponseEntity<List<ResponseTaskDto>> listTasks() {
        List<ResponseTaskDto> listTasks = this.taskService.listTasks();

        return ResponseEntity.ok(listTasks);
    }

    @Operation(description = "Atualiza a tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada"),
            @ApiResponse(responseCode = "404", description = "Não existe tarefa com o id informado")
    })
    @PutMapping("/{id}")
    public ResponseEntity updateTask(@PathVariable UUID id, @RequestBody @Valid UpdateTaskDto data) {
        this.taskService.updateTask(id, data);

        return ResponseEntity.ok().build();
    }

    @Operation(description = "Retorna a tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a tarefa"),
            @ApiResponse(responseCode = "404", description = "Não existe tarefa com o id informado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTaskDto> getTaskById(@PathVariable UUID id) {
        Task task = this.taskService.getTaskById(id);

        return ResponseEntity.ok(TaskMapper.toDto(task));
    }

    @Operation(description = "Deleta a tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleta a tarefa"),
            @ApiResponse(responseCode = "404", description = "Não existe tarefa com o id informado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteTaskById(@PathVariable UUID id) {
        this.taskService.deleteTaskById(id);

        return ResponseEntity.ok().build();
    }

    @Operation(description = "Completa a tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Completa a tarefa"),
            @ApiResponse(responseCode = "404", description = "Não existe tarefa com o id informado")
    })
    @PutMapping("/{id}/completed")
    public ResponseEntity taskCompleted(@PathVariable UUID id) {
        this.taskService.completeOrReopenTask(id);

        return ResponseEntity.ok().build();
    }

    @Operation(description = "Reabre a tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reabre a tarefa"),
            @ApiResponse(responseCode = "404", description = "Não existe tarefa com o id informado")
    })
    @PutMapping("/{id}/reopen-task")
    public ResponseEntity reopenTask(@PathVariable UUID id) {
        this.taskService.completeOrReopenTask(id);

        return ResponseEntity.ok().build();
    }

    @Operation(description = "Retorna as tarefas concluídas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as tarefas concluídas"),
    })
    @GetMapping("/completed")
    public ResponseEntity<List<ResponseTaskDto>> listTaskCompleted(){
        List<ResponseTaskDto> accomplishedTrue = this.taskService.listTaskByCompletionStatus(true);

        return ResponseEntity.ok(accomplishedTrue);
    }

    @Operation(description = "Retorna as tarefas não concluídas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as tarefas não concluídas"),
    })
    @GetMapping("/incompleted")
    public ResponseEntity<List<ResponseTaskDto>> listTaskIncompleted(){
        List<ResponseTaskDto> accomplishedFalse = this.taskService.listTaskByCompletionStatus(false);

        return ResponseEntity.ok(accomplishedFalse);
    }
}
