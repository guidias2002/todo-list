package com.example.todoList.domain;

import com.example.todoList.dto.RequestTaskDto;
import com.example.todoList.dto.ResponseTaskDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Boolean accomplished = false;

    @Min(1)
    private Integer priority;


    public Task(RequestTaskDto data){
        this.name = data.name();
        this.description = data.description();
        this.priority = data.priority();
    }

}
