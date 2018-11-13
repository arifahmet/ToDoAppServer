package com.arif.backenddemo.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public RegisterRequest(){}

    public RegisterRequest(@NotBlank String name, @NotBlank String username, @NotBlank String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
