package com.arif.backenddemo.controller;

import com.arif.backenddemo.domain.ToDoItem;
import com.arif.backenddemo.domain.ToDoList;
import com.arif.backenddemo.model.ApiResponse;
import com.arif.backenddemo.model.CreateToDoItemRequest;
import com.arif.backenddemo.service.ToDoItemService;
import com.arif.backenddemo.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/todoitem")
public class ToDoItemController {

    @Autowired
    private ToDoItemService toDoItemService;

    @Autowired
    private ToDoListService toDoListService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createToDoItem(@RequestBody @Validated CreateToDoItemRequest createToDoItemRequest, Principal principal){
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setStatus(false);
        toDoItem.setName(createToDoItemRequest.getName());
        toDoItem.setDeadline(createToDoItemRequest.getDeadline());
        ToDoList toDoList = toDoListService.findToDoListById(createToDoItemRequest.getToDoListId());
        if(toDoList == null){
            return new ResponseEntity("No To-Do List found", HttpStatus.BAD_REQUEST);
        }
        if(!principal.getName().equals(toDoList.getUser().getUsername())){
            return new ResponseEntity("You cannot add todoitem to this todolist", HttpStatus.BAD_REQUEST);
        }
        toDoItem.setToDoList(toDoList);
        toDoItem = toDoItemService.createToDoItem(toDoItem);
        if(toDoItem == null){
            return new ResponseEntity("To-Do Item Add fail.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(toDoItem, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity listToDoItems(@RequestParam("todoListId")Long id){
        ToDoList toDoList = toDoListService.findToDoListById(id);
        if(toDoList == null){
            return new ResponseEntity("No To-Do List found", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(toDoItemService.getAllToDoItemsByToList(toDoList.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity deleteToDoItem(@RequestParam("id") Long id, Principal principal){

        ApiResponse apiResponse = toDoItemService.deleteTodoItem(id);
        if(!apiResponse.getStatus()){
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    public ResponseEntity completeToDoItem(@RequestParam("id") Long id, Principal principal){

        ApiResponse apiResponse = toDoItemService.changeStatus(id);
        if(!apiResponse.getStatus()){
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }
}
