package com.example.todoList.domain;

import com.example.todoList.dto.RequestTaskDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String description;
    private Boolean accomplished = false;
    private Integer priority;


    public Task(RequestTaskDto data){
        this.name = data.name();
        this.description = data.description();
        this.priority = data.priority();
    }
}
