package com.example.the_bugtracker_mark_2.Services;


import com.example.the_bugtracker_mark_2.Configs.ValueNotFoundException;
import com.example.the_bugtracker_mark_2.Models.Activity;
import com.example.the_bugtracker_mark_2.Repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    public List<Activity> findAllActivities(){
        return activityRepository.findAll();
    }

    public void createActivity(Activity activity){
        activityRepository.save(activity);
    }

    public Activity get(Integer id) throws ValueNotFoundException {

        try {
            return activityRepository.findById(id).get();
        } catch (NoSuchElementException exception) {
            throw new ValueNotFoundException("Could not find any bug with ID " + id);
        }
    }

    public List<Activity> getActivityByBugId(int bugId){
        return activityRepository.activityOwningBug(bugId);
    }


    public List<Activity> activityFilterByAction(int bugActionId){
        return activityRepository.activityFilterByAction(bugActionId);
    }

}
