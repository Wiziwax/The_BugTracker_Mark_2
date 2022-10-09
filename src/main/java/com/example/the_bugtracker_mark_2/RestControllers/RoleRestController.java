package com.example.the_bugtracker_mark_2.RestControllers;


import com.example.the_bugtracker_mark_2.Models.Role;
import com.example.the_bugtracker_mark_2.Repositories.RoleRepository;
import com.example.the_bugtracker_mark_2.Repositories.UserRepository;
import com.example.the_bugtracker_mark_2.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
@CrossOrigin(origins = "http://10.128.32.141:4200")
public class RoleRestController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRepository userRepository;


    @GetMapping("")
    public List<Role> displayRoles(Model model){
        return roleService.listAllRoles();
    }

    @GetMapping("new")
    public void displayRoleForm(Model model) {

        Role aRole = new Role();
        List<Role> roleList = roleRepository.findAll();
        model.addAttribute("allRoles", roleList);
        model.addAttribute("role", aRole);
    }

    @PostMapping("save")
    public Role createRoleForm(Role role) {
        return roleRepository.save(role);
    }
}
