package com.arif.backenddemo.service.impl;

import com.arif.backenddemo.domain.ToDoItem;
import com.arif.backenddemo.domain.ToDoList;
import com.arif.backenddemo.model.ApiResponse;
import com.arif.backenddemo.model.ToDoItemResponse;
import com.arif.backenddemo.repository.ToDoItemRepository;
import com.arif.backenddemo.repository.ToDoListRepository;
import com.arif.backenddemo.service.DependencyService;
import com.arif.backenddemo.service.ToDoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ToDoItemServiceImpl implements ToDoItemService{
    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @Autowired
    private DependencyService dependencyService;

    @Override
    public List<ToDoItemResponse> getAllToDoItemsByToList(Long toDoListId) {
        List<ToDoItemResponse> responseList = new ArrayList<>();
        List<ToDoItemResponse> dependencies;
        ToDoItemResponse responseItem;
        ToDoItemResponse dependencyItem;

        List<ToDoItem> list = toDoItemRepository.findByToDoListId(toDoListId);
        for (ToDoItem toDoItem:list
             ) {
            responseItem = createToDoItemResponse(toDoItem);
            dependencies = new ArrayList<>();
            for (ToDoItem dependency:toDoItem.getDependency()
                 ) {
                dependencyItem = createToDoItemResponse(dependency);
                dependencies.add(dependencyItem);
            }
            responseItem.setDependencies(dependencies);
            responseList.add(responseItem);
        }
        return responseList;
    }

    private ToDoItemResponse createToDoItemResponse(ToDoItem item){
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date today = new Date();
        today.setHours(0);
        String status;
        ToDoItemResponse responseItem;
        responseItem = new ToDoItemResponse();
        responseItem.setId(item.getId());
        responseItem.setName(item.getName());
        responseItem.setDeadline(df.format(item.getDeadline()));
        if(item.getStatus()){
            status = "Completed";
        }else if(item.getDeadline().before(today)){
            status = "Expired";
        }else{
            status = "Not Completed";
        }
        responseItem.setStatus(status);
        responseItem.setDependencies(new ArrayList<>());
        return responseItem;
    }
    @Override
    public ToDoItem createToDoItem(ToDoItem toDoItem) {
        return toDoItemRepository.save(toDoItem);
    }

    @Override
    @Transactional
    public ApiResponse deleteTodoItem(Long id) {
        ToDoItem toDoItem = toDoItemRepository.findById(id).orElse(null);
        if(toDoItem == null){
            return new ApiResponse("No To-Do Item found", false);
        }
        dependencyService.deleteDependencies(id);
        toDoItemRepository.delete(toDoItem);
        return new ApiResponse("To-Do Item successfully deleted.", true);
    }

    private Boolean checkAllDependiciesDone(List<ToDoItem> dependicies){
        for (ToDoItem toDoItem: dependicies
             ) {
            if(!toDoItem.getStatus()){
                return false;
            }
        }
        return true;
    }
    @Override
    public ApiResponse changeStatus(Long id) {
        ToDoItem toDoItem = toDoItemRepository.findById(id).orElse(null);
        if(toDoItem == null){
            return new ApiResponse("No To-Do Item found", false);
        }
        if(!checkAllDependiciesDone(toDoItem.getDependency())){
            return new ApiResponse("To-Do Item cannot change status to done before all dependencies is done", false);
        }
        if(toDoItem.getStatus()){
            return new ApiResponse("To-Do Item cannot change because it is already completed.", false);
        }
        toDoItem.setStatus(true);
        toDoItemRepository.save(toDoItem);
        return new ApiResponse("To-Do Item successfully deleted.", true);
    }

    @Override
    public ToDoItem getToDoItem(Long id) {
        return toDoItemRepository.findById(id).orElse(null);
    }
}
