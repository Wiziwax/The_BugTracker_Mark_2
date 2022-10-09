package com.example.the_bugtracker_mark_2.Controllers;


import com.example.the_bugtracker_mark_2.Models.Role;
import com.example.the_bugtracker_mark_2.Repositories.UserRepository;
import com.example.the_bugtracker_mark_2.Repositories.RoleRepository;
import com.example.the_bugtracker_mark_2.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("roles")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String displayRoles(Model model){
        List<Role> roles = roleService.listAllRoles();
        model.addAttribute("roles", roles);

        return "roles/list-roles";
    }

    @GetMapping("new")
    public String displayRoleForm(Model model) {

        Role aRole = new Role();
        List<Role> roleList = roleRepository.findAll();
        model.addAttribute("allRoles", roleList);
        model.addAttribute("role", aRole);
        return "roles/new-roles";
    }

    @PostMapping("save")
    public String createRoleForm(Role role) {
        roleRepository.save(role);
        //Use a redirect to prevent duplicate submissions

        return "redirect:/roles/";
    }
}