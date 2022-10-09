package com.example.the_bugtracker_mark_2.Controllers;

import com.example.the_bugtracker_mark_2.Services.ActivityService;
import com.example.the_bugtracker_mark_2.Configs.ValueNotFoundException;
import com.example.the_bugtracker_mark_2.Models.Activity;
import com.example.the_bugtracker_mark_2.Models.Bug;
import com.example.the_bugtracker_mark_2.Services.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;
    @Autowired
    BugService bugService;

    @GetMapping("")
    public String displayActivities( Model model) throws ValueNotFoundException {
        List<Activity> activityList = activityService.findAllActivities();
        List<Bug> bugActivityList = bugService.bugList();
        model.addAttribute("activityList", activityList);
        model.addAttribute("bugActivityList", bugActivityList);
        return "activity/activity_list";
    }
}