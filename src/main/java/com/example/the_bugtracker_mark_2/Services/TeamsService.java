package com.example.the_bugtracker_mark_2.Services;

import com.example.the_bugtracker_mark_2.Models.Teams;
import com.example.the_bugtracker_mark_2.Models.User;
import com.example.the_bugtracker_mark_2.Repositories.TeamsRepository;
import com.example.the_bugtracker_mark_2.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamsService {

    @Autowired
    TeamsRepository teamsRepository;

    @Autowired
    UserRepository userRepository;

    public List<Teams>getAllTeams(){
        return teamsRepository.findAll();
    }

    public List<User> listOfUsersInTeam(int teamId){
        return userRepository.getUsersByUserTeam(teamId);
    }

}
