package com.example.todoList.service;

import com.example.todoList.domain.Task;
import com.example.todoList.dto.RequestTaskDto;
import com.example.todoList.dto.ResponseTaskDto;
import com.example.todoList.dto.UpdateTaskDto;
import com.example.todoList.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                .orElseThrow(() -> new RuntimeException());

        if(data.name() != null){
            task.setName(data.name());
        }

        if(data.description() != null){
            task.setDescription(data.description());
        }

        if(data.accomplished() != null){
            task.setAccomplished(data.accomplished());
        }

        if(data.priority() != null){
            task.setPriority(data.priority());
        }

        this.taskRepository.save(task);
    }

    public Optional<Task> getTaskById(UUID id){
        Optional<Task> task = this.taskRepository.findById(id);

        return task;
    }
}
