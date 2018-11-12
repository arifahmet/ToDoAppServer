package com.arif.backenddemo.service.impl;

import com.arif.backenddemo.domain.Dependency;
import com.arif.backenddemo.domain.ToDoItem;
import com.arif.backenddemo.model.ApiResponse;
import com.arif.backenddemo.model.CreateDependencyRequest;
import com.arif.backenddemo.repository.DependencyRepository;
import com.arif.backenddemo.service.DependencyService;
import com.arif.backenddemo.service.ToDoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DependencyServiceImpl implements DependencyService {

    @Autowired
    private DependencyRepository dependencyRepository;

    @Autowired
    private ToDoItemService toDoItemService;

    @Override
    public ApiResponse createDependeency(CreateDependencyRequest createDependencyRequest) {
        if(createDependencyRequest.getDependencyId() == createDependencyRequest.getToDoItemId()){
            return new ApiResponse("Cannot create a paradox. :)", false);
        }
        ToDoItem dependency = toDoItemService.getToDoItem(createDependencyRequest.getDependencyId());
        if(dependency == null){
            return new ApiResponse("No Dependency To-Do Item found.", false);
        }
        ToDoItem toDoItem = toDoItemService.getToDoItem(createDependencyRequest.getToDoItemId());
        if(dependency == null){
            return new ApiResponse("No To-Do Item found.", false);
        }
        if(findParadox(dependency.getDependency(), toDoItem.getId())){
            return new ApiResponse("Cannot create a paradox. :)", false);
        }
        dependencyRepository.save(new Dependency(toDoItem.getId(), dependency.getId()));

        return new ApiResponse("Dependency successfully created.", true);
    }

    private Boolean findParadox(List<ToDoItem> source, Long targetId){
        if(source == null){
            return false;
        }
        Boolean result = false;
        for (ToDoItem toDoItem: source
             ) {
            if(toDoItem.getId() == targetId){
                return true;
            }
            result = findParadox(toDoItem.getDependency(), targetId);
            if(result){
                return true;
            }
        }
        return false;
    }
    @Override
    @Transactional
    public ApiResponse deleteDependency(Long dependencyId) {
        Dependency dependency = dependencyRepository.findById(dependencyId).orElse(null);
        if(dependency == null){
            return new ApiResponse("No Dependency Item found", false);
        }
        dependencyRepository.delete(dependency);
        return new ApiResponse("To-Do Item successfully deleted.", true);
    }

    @Override
    @Transactional
    public void deleteDependencies(Long toDoItemId) {
        List<Dependency> list = dependencyRepository.findByToDoItemIdOrDependencyToDoItemId(toDoItemId, toDoItemId);
        for (Dependency dependency:list
                ) {
            dependencyRepository.delete(dependency);
        }
    }


}
