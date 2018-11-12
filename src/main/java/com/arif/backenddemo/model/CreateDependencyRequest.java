package com.arif.backenddemo.model;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class CreateDependencyRequest {

    @NonNull
    private Long dependencyId;
    @NonNull
    private Long toDoItemId;

    public CreateDependencyRequest(){}

    public CreateDependencyRequest(@NotBlank Long dependencyId, Long toDoItemId) {
        this.dependencyId = dependencyId;
        this.toDoItemId = toDoItemId;
    }
}
