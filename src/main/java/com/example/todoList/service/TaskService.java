package com.example.todoList.service;

import com.example.todoList.domain.Task;
import com.example.todoList.dto.RequestTaskDto;
import com.example.todoList.dto.ResponseTaskDto;
import com.example.todoList.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

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
}
