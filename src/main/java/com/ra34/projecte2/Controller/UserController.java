package com.ra34.projecte2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ra34.projecte2.DTO.UserDTO;
import com.ra34.projecte2.DTO.UserRequest;
import com.ra34.projecte2.Service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createuser")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRequest request) {        
        UserDTO response = userService.createUserAndCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}