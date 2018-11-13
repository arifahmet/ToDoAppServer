package com.arif.backenddemo.service.impl;

import com.arif.backenddemo.domain.ToDoItem;
import com.arif.backenddemo.domain.ToDoList;
import com.arif.backenddemo.domain.User;
import com.arif.backenddemo.model.ApiResponse;
import com.arif.backenddemo.model.ToDoItemResponse;
import com.arif.backenddemo.repository.ToDoListRepository;
import com.arif.backenddemo.service.ToDoItemService;
import com.arif.backenddemo.service.ToDoListService;
import com.arif.backenddemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToDoListServiceImpl implements ToDoListService {

    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private ToDoItemService toDoItemService;


    @Override
    public ToDoList create(ToDoList toDoList) {

        return toDoListRepository.save(toDoList);
    }

    @Override
    public List<ToDoList> getAllByUser(User user) {
        List<ToDoList> response = new ArrayList<>();
        List<ToDoList> list = new ArrayList<>();

        response = toDoListRepository.findByUser(user);

        for (ToDoList toDoList: response
             ) {
            toDoList.setUser(null);
            list.add(toDoList);
        }
        return list;
    }

    @Override
    public ToDoList findToDoListById(Long id) {
        return toDoListRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public ApiResponse deleteTodoList(Long id, String username) {
        ToDoList toDoList = toDoListRepository.findById(id).orElse(null);
        if(toDoList == null){
            return new ApiResponse("No To-Do List Found", false);
        }
        if(!toDoList.getUser().getUsername().equals(username)){
            return new ApiResponse("You don't have permission", false);
        }
        List<ToDoItemResponse> toDoItemList = toDoItemService.getAllToDoItemsByToDoList(toDoList.getId());
        for (ToDoItemResponse toDoItem:toDoItemList
             ) {
         toDoItemService.deleteTodoItem(toDoItem.getId());
        }
        toDoListRepository.delete(toDoList);
        return new ApiResponse("To-Do List successfully deleted.", true);
    }
}
