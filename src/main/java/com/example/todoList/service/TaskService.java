package com.example.todoList.service;

import com.example.todoList.config.exceptions.TaskNotFoundException;
import com.example.todoList.config.exceptions.ValidationException;
import com.example.todoList.domain.Task;
import com.example.todoList.dto.RequestTaskDto;
import com.example.todoList.dto.ResponseTaskDto;
import com.example.todoList.dto.UpdateTaskDto;
import com.example.todoList.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(RequestTaskDto data) {
        Task newTask = new Task(data);

        this.taskRepository.save(newTask);
    }

    public List<ResponseTaskDto> listTasks(){
        List<ResponseTaskDto> listTasks = this.taskRepository.findAll()
                .stream().map(task -> new ResponseTaskDto(task.getId(), task.getName(), task.getDescription(), task.getAccomplished(), task.getPriority()))
                .sorted(Comparator.comparing(ResponseTaskDto::priority).reversed()
                        .thenComparing(ResponseTaskDto::name))
                .toList();

        return listTasks;
    }

    public void updateTask(UUID id, UpdateTaskDto data){
        Task task = this.taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException());

        Map<String, String> errors = new HashMap();


        if(data.name() != null){
            if(data.name().trim().isEmpty()){
                errors.put("name", "Name cannot be blank");
            }

            task.setName(data.name());
        }

        if(data.description() != null){
            if(data.description().trim().isEmpty()){
                errors.put("description", "Description cannot be blank");
            }

            task.setDescription(data.description());
        }

        if(data.priority() != null){
            if(data.priority() < 1 || data.priority() > 5){
                errors.put("priority", "Priority must be between 1 and 5");
            }

            task.setPriority(data.priority());
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        this.taskRepository.save(task);
    }

    public Optional<Task> getTaskById(UUID id){
        Optional<Task> task = this.taskRepository.findById(id);

        return task;
    }

    public void deleteTaskById(UUID id){
        Optional<Task> task = this.taskRepository.findById(id);

        if(!task.isPresent()){
            throw new RuntimeException("Task not found");
        }

        this.taskRepository.deleteById(id);
    }

    public void taskCompleted(UUID id){
        Task task = this.taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());

        task.setAccomplished(true);
        this.taskRepository.save(task);
    }

    public List<ResponseTaskDto> listTaskByCompletionStatus(Boolean value){
        List<ResponseTaskDto> listTasks = this.taskRepository.findByAccomplished(value)
                .stream().map(task -> new ResponseTaskDto(task.getId(), task.getName(), task.getDescription(), task.getAccomplished(), task.getPriority()))
                .sorted(Comparator.comparing(ResponseTaskDto::priority).reversed()
                        .thenComparing(ResponseTaskDto::name))
                .toList();

        return listTasks;
    }

}
