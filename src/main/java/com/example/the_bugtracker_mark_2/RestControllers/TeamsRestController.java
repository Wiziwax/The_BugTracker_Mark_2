package com.example.the_bugtracker_mark_2.RestControllers;

import com.example.the_bugtracker_mark_2.Models.Teams;
import com.example.the_bugtracker_mark_2.Models.User;
import com.example.the_bugtracker_mark_2.Services.TeamsService;
import com.example.the_bugtracker_mark_2.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/teams")
@CrossOrigin(origins = "http://10.128.32.201:4200")
public class TeamsRestController {

    @Autowired
    TeamsService teamsService;

    @Autowired
    UserService userService;

    @GetMapping("allteams")
    public List<Teams> listOfTeams(){
        return teamsService.getAllTeams();
    }


    @GetMapping("allusersonteams")
    public List<User> listOfUsersOnTeams(@RequestParam int teamId){
        return teamsService.listOfUsersInTeam(teamId);
    }
}
