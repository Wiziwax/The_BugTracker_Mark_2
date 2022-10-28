package com.example.the_bugtracker_mark_2.RestControllers;

import com.example.the_bugtracker_mark_2.Models.Role;
import com.example.the_bugtracker_mark_2.Models.User;
import com.example.the_bugtracker_mark_2.Repositories.RoleRepository;
import com.example.the_bugtracker_mark_2.Repositories.UserRepository;
import com.example.the_bugtracker_mark_2.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/usersignup")
@CrossOrigin(origins = "http://10.128.32.201:4200")
public class UserSignupRestController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;


    @PostMapping("save")
    public void createUserForm(@RequestBody User user) {
        userService.save(user);
    }

}
