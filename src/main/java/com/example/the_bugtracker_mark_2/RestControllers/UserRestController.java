package com.example.the_bugtracker_mark_2.RestControllers;

import com.example.the_bugtracker_mark_2.Models.Bug;
import com.example.the_bugtracker_mark_2.Models.Role;
import com.example.the_bugtracker_mark_2.Models.User;
import com.example.the_bugtracker_mark_2.Repositories.BugRepository;
import com.example.the_bugtracker_mark_2.Repositories.UserRepository;
import com.example.the_bugtracker_mark_2.Configs.ValueNotFoundException;
import com.example.the_bugtracker_mark_2.Repositories.RoleRepository;
import com.example.the_bugtracker_mark_2.Services.BugService;
import com.example.the_bugtracker_mark_2.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://10.128.32.201:4200")
public class UserRestController {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BugRepository bugRepository;

    @Autowired
    BugService bugService;

    //DISPLAY ALL USERS
    @GetMapping("")
    public List<User> displayUsers() {
        return userService.listAll();
    }

    //ROLE LIST
    @GetMapping("rolelist")
    public List<Role> roleList() {
        return roleRepository.findAll();
    }


    //FIND USER BY ID
    @GetMapping("findbyid")
    public Optional<User> findUser(@RequestParam Integer id){
        return userService.getUserById(id);
    }


    //CREATE USER
    @PostMapping("addUser")
    public void createUserForm(@RequestBody User user) {
        userService.save(user);
    }


    //DELETE USER
    @PostMapping("delete")
    public void deleteUsers(@RequestBody User user){
        Integer id = user.getId();
        System.out.println(id);
        userService.deleteUser(id);
    }

    //UPDATE USER
    @PutMapping("update")
    public void updateUser(@RequestBody User user) throws ValueNotFoundException {
        userService.updateUserRestController(user.getId(), user.getPassword(),
                user.getFirstName(), user.getLastName(), user.getEmail());
    }


    // ENABLE/DISABLE USER
    @GetMapping("/enabled/{id}")
    public void updateUserEnabledStatus(@PathVariable("id") Integer id,
                                        boolean enabled
                                        ) throws ValueNotFoundException {
        User user=userService.get(id);
        if(user.isEnabled()){
        user.setEnabled(false);
        }else {user.setEnabled(true);}

        enabled= user.isEnabled()?true:false;
        userService.updateUserEnabledStatus(id, enabled);
    }


    @GetMapping("mydashboard/{id}")
    public List <Bug> myDashboard(@PathVariable Integer id) throws ValueNotFoundException {
        return bugService.getBugByUserId(id);
    }

    @PutMapping("update/{userId}")
    public void updateUser(
            @PathVariable("userId") Integer userId,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String email
            ) {
            System.out.println();
        userService.updateUserRestController(userId, firstName, password, lastName, email);
    }

}
