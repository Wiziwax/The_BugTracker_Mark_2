package com.example.the_bugtracker_mark_2.Controllers;


import com.example.the_bugtracker_mark_2.Models.Role;
import com.example.the_bugtracker_mark_2.Models.User;
import com.example.the_bugtracker_mark_2.Repositories.UserRepository;
import com.example.the_bugtracker_mark_2.Repositories.RoleRepository;
import com.example.the_bugtracker_mark_2.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("usersignup")
public class UserSignupController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;





    @GetMapping("new")
    public String newUserForm(Model model) {

        User aUser = new User();
        List<Role> roleList = roleRepository.findAll();
        Role role = roleList.get(2);
        model.addAttribute("allRoles", roleList);
        model.addAttribute("user", aUser);
        return "users/new-users";

    }

    @PostMapping("save")
    public String createUserForm(User user) {
        userService.save(user);
        return "redirect:/users/";
    }
}
