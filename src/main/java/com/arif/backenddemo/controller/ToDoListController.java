package com.arif.backenddemo.controller;

import com.arif.backenddemo.domain.ToDoList;
import com.arif.backenddemo.model.ApiResponse;
import com.arif.backenddemo.service.ToDoListService;
import com.arif.backenddemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/todolist")
public class ToDoListController {


    @Autowired
    private ToDoListService toDoListService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ToDoList> createToDoList(@RequestBody @Validated ToDoList toDoList, Principal principal){
        toDoList.setUser(userService.getUserByUsername(principal.getName()));
        toDoList = toDoListService.create(toDoList);

        return ResponseEntity.ok(toDoList);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<ToDoList>> getToDoList(Principal principal){

        List<ToDoList> toDoListList = toDoListService.getAllByUser(userService.getUserByUsername(principal.getName()));

        return ResponseEntity.ok(toDoListList);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity deleteToDoList(@RequestParam("id") Long id, Principal principal){

        ApiResponse apiResponse = toDoListService.deleteTodoList(id, principal.getName());
        if(!apiResponse.getStatus()){
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }
}
