package com.arif.backenddemo.service;

import com.arif.backenddemo.domain.ToDoList;
import com.arif.backenddemo.domain.User;
import com.arif.backenddemo.model.ApiResponse;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public interface ToDoListService {

    ToDoList create(ToDoList toDoList);
    List<ToDoList> getAllByUser(User user);
    ToDoList findToDoListById(Long id);
    ApiResponse deleteTodoList(Long id, String username);
}
