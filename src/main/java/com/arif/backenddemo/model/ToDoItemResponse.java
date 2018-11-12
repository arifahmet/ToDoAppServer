package com.arif.backenddemo.model;

import lombok.Data;

import java.util.List;


@Data
public class ToDoItemResponse {
    private Long id;
    private String name;
    private String deadline;
    private String status;
    private List<ToDoItemResponse> dependencies;
    public ToDoItemResponse(){

    }
}
