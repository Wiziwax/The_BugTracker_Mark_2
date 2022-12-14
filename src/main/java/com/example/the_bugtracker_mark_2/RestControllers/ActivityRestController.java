package com.example.the_bugtracker_mark_2.RestControllers;


import com.example.the_bugtracker_mark_2.Services.ActivityService;
import com.example.the_bugtracker_mark_2.Models.Activity;
import com.example.the_bugtracker_mark_2.Services.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/activity")
@CrossOrigin(origins = "http://10.128.32.201:4200")
public class ActivityRestController {

    @Autowired
    ActivityService activityService;

    @Autowired
    BugService bugService;

    @GetMapping("")
    public List<Activity> displayActivities(){
        return activityService.findAllActivities();
    }

    @GetMapping("activityfilter/{id}")
    public List<Activity> activitiesFilter(@PathVariable Integer id) {
        return activityService.activityFilterByAction(id);
    }
}
