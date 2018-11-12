package com.arif.backenddemo.service;

import com.arif.backenddemo.domain.Dependency;
import com.arif.backenddemo.model.ApiResponse;
import com.arif.backenddemo.model.CreateDependencyRequest;
import com.arif.backenddemo.model.ToDoItemResponse;

import java.util.List;

public interface DependencyService {
    ApiResponse createDependeency(CreateDependencyRequest createDependencyRequest);
    ApiResponse deleteDependency(Long dependencyId);
    void deleteDependencies(Long toDoItemId);
}
