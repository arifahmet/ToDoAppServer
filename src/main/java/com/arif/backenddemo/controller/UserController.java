package com.arif.backenddemo.controller;

import com.arif.backenddemo.domain.User;
import com.arif.backenddemo.model.ApiResponse;
import com.arif.backenddemo.service.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/checkusernameavailability", method = RequestMethod.GET)
    public ResponseEntity checkUsernameAvailability(@RequestParam("username") String username){
        User user = userService.getUserByUsername(username);

        if(user == null){
            return new ResponseEntity(new ApiResponse("Username is available", true), HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse("Username is not available", false), HttpStatus.OK);
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<User> currentUserName(Principal principal) {
        User aUser = userService.getUserByUsername(principal.getName());
        aUser.setPassword("");
        aUser.setAuthorities("");
        return ResponseEntity.ok(aUser);
    }
}
