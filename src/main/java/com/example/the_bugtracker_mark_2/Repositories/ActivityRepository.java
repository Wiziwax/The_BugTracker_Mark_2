package com.example.the_bugtracker_mark_2.Repositories;

import com.example.the_bugtracker_mark_2.Models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://10.128.32.141:4200")
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {


    @Query(nativeQuery = true, value = "SELECT * FROM activity a WHERE a.activity_bug_id=:bugId")
    public List<Activity> activityOwningBug(int bugId);



    @Query(nativeQuery = true, value = "SELECT * FROM tracktesty.activity where bug_action =:bugActionId")
    public List<Activity> activityFilterByAction(int bugActionId);

    }
