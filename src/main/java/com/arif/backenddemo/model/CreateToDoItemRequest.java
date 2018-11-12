package com.arif.backenddemo.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class CreateToDoItemRequest {

    @NotBlank
    private String name;
    @NonNull
    private Date deadline;
    @NonNull
    private Long toDoListId;

    public CreateToDoItemRequest(){}

    public CreateToDoItemRequest(String name, Date deadline, Long toDoListId) {
        this.name = name;
        this.deadline = deadline;
        this.toDoListId = toDoListId;
    }
}
