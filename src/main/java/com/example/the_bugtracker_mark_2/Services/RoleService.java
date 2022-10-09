package com.example.the_bugtracker_mark_2.Services;


import com.example.the_bugtracker_mark_2.Models.Role;
import com.example.the_bugtracker_mark_2.Repositories.UserRepository;
import com.example.the_bugtracker_mark_2.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public List<Role> listAllRoles(){
        return roleRepository.findAll();
    }

}
