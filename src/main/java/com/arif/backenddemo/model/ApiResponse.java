package com.arif.backenddemo.model;

import lombok.Data;

@Data
public class ApiResponse {
    private String message;
    private  Boolean status;

    public ApiResponse(){}

    public ApiResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
}
