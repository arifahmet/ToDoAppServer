package com.arif.backenddemo.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateToDoListRequest {
    @NotBlank
    private String name;

    public CreateToDoListRequest(){}

    public CreateToDoListRequest(String name) {
        this.name = name;
    }
}
