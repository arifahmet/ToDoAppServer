package com.arif.backenddemo.service;


import com.arif.backenddemo.domain.ToDoItem;
import com.arif.backenddemo.model.ApiResponse;
import com.arif.backenddemo.model.ToDoItemResponse;

import java.util.List;

public interface ToDoItemService {
    List<ToDoItemResponse> getAllToDoItemsByToDoList(Long toDoListId);
    ToDoItem createToDoItem(ToDoItem toDoItem);
    ApiResponse deleteTodoItem(Long id);
    ApiResponse changeStatus(Long id);
    ToDoItem getToDoItem(Long id);
}
