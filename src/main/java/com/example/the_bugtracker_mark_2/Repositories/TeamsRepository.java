package com.example.the_bugtracker_mark_2.Repositories;

import com.example.the_bugtracker_mark_2.Models.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;


@Repository
@CrossOrigin(origins = "http://10.128.32.201:4200")
public interface TeamsRepository extends JpaRepository<Teams, Integer> {



}
