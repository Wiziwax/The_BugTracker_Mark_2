package com.example.the_bugtracker_mark_2.RestControllers;

import com.example.the_bugtracker_mark_2.Configs.ValueNotFoundException;
import com.example.the_bugtracker_mark_2.Models.User;
import com.example.the_bugtracker_mark_2.Repositories.RoleRepository;
import com.example.the_bugtracker_mark_2.Repositories.UserRepository;
import com.example.the_bugtracker_mark_2.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/usersignin")
@CrossOrigin(origins = "http://10.128.32.201:4200")
public class UserSigninRestController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;


    @PostMapping("login")
    public void createUserForm(@RequestBody User user,
                               Model model)throws ValueNotFoundException {

        String signInUsername = user.getEmail();
        String signInPassword = user.getPassword();
        String message = "Successfully Logged in";
        String message1 = "Username or password incorrect";
        User userCheck = userRepository.getByEmail(signInUsername);

        if (userCheck != null) {
            model.addAttribute("message", message);
        }
        else {
        throw new UsernameNotFoundException("Could not find user with email: " + signInUsername);
        }



        if(signInUsername==userCheck.getEmail()&&signInPassword==userCheck.getPassword()){
            model.addAttribute("message",message);
        }

        else {
            model.addAttribute("message1", message1);
        }

        System.out.println(message);
    }



    @PostMapping("checkifuserexists")
    public User checkIfExists(@RequestBody User user) throws ValueNotFoundException {
        User usernameExists=userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

        if(usernameExists==null){
            throw new ValueNotFoundException("Username or password incorrect");
        }
        return usernameExists;
    }
}
