package com.ra34.projecte2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ra34.projecte2.DTO.UserDTO;
import com.ra34.projecte2.DTO.UserRequest;
import com.ra34.projecte2.Service.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRequest request) {        
        UserDTO response = userService.createUserAndCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@RequestParam Long id) {
        UserDTO response = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("users/{id}")
    public ResponseEntity <UserDTO> updateUser(@PathVariable Long id, @RequestBody UserRequest request){
        UserDTO updatedUser = userService.updateUserInfo(id,request) ;
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("users/roles/{id}")
    public ResponseEntity<UserDTO> addRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        UserDTO response = userService.addRolesToUser(id, roleIds);
        return ResponseEntity.ok(response);
    }
    

}