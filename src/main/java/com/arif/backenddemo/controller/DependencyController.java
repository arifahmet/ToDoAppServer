package com.arif.backenddemo.controller;

import com.arif.backenddemo.domain.ToDoItem;
import com.arif.backenddemo.domain.ToDoList;
import com.arif.backenddemo.model.ApiResponse;
import com.arif.backenddemo.model.CreateDependencyRequest;
import com.arif.backenddemo.model.CreateToDoItemRequest;
import com.arif.backenddemo.model.ToDoItemResponse;
import com.arif.backenddemo.service.DependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/dependency")
public class DependencyController {

    @Autowired
    private DependencyService dependencyService;
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody @Validated CreateDependencyRequest createDependencyRequest, Principal principal){
        ApiResponse apiResponse = dependencyService.createDependeency(createDependencyRequest);
        if(!apiResponse.getStatus()){
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity deleteDependency(@RequestParam("id") Long id, Principal principal){

        ApiResponse apiResponse = dependencyService.deleteDependency(id);
        if(!apiResponse.getStatus()){
            return new ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }

}
