package com.example.the_bugtracker_mark_2.Services;

import com.example.the_bugtracker_mark_2.Configs.ValueNotFoundException;
import com.example.the_bugtracker_mark_2.Enums.Action;
import com.example.the_bugtracker_mark_2.Enums.Severity;
import com.example.the_bugtracker_mark_2.Models.*;
import com.example.the_bugtracker_mark_2.Repositories.ActivityRepository;
import com.example.the_bugtracker_mark_2.Repositories.BugRepository;
import com.example.the_bugtracker_mark_2.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class BugService {

    @Autowired
    BugRepository bugRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ActivityService activityService;


    //LIST ALL BUGS
    public List<Bug> bugList(){
        return bugRepository.findAll();
    }

    //PENDING APPROVAL LIST
    public List<Bug> listOfPendingApprovals(){
        return bugRepository.listOfPendingApprovals();
    }

//
    public String[] findBugNames(){
        return getStrings();
    }


    private String[] getStrings() {
        List<Bug> bugNames = bugRepository.findAll();
        String theNames[]=new String[bugNames.size()];
        for(int i=0; i<bugNames.size();i++){
            theNames[i] = bugNames.get(i).getLabel();
        }
        return theNames;
    }


    public String[] findBugReviews(){
        return getStrings();
    }

    //PAGEABLE LIST
    public Page<Bug> listAll(Pageable pageable) {
        return bugRepository.findAll(pageable);
    }

    public List<Bug> listAssignedBugs(){
        return bugRepository.getBugByUserAssignedToBugIsNotNull();
    }

//    public String getSignedInUsername(
////            @AuthenticationPrincipal
//                                      SecurityUser userDetails){
//        String userEmail = userDetails.getUsername();
//        User user = userRepository.getByEmail(userEmail);
//        return user.getFirstName() + " "+ user.getLastName();
//    }


    //CREATE BUG
    public void create(Bug bug){

        Bug buggy = bugRepository.save(bug);

        //todo insert a new activity
        Activity activity = new Activity(
                String.format("Bug created by %s with description %s",buggy.getCreatedBy(), buggy.getBugReview()),
                buggy.getCreatedBy(),
                buggy.getReportDate(),
                buggy.getApprovedBy(),
                buggy.getApprovedDate(),
                buggy.getAssignedTo(),
                buggy.getTicketId(),
                buggy.getBugTreatmentStage(),
                buggy.getProgressStatus());

        activity.setBugActivity(buggy);
        activity.setAction(Action.BUG_CREATION);
        buggy.setEnumSeverity(Severity.LOW);
        activityRepository.save(activity);

    }

    //GET BY ID FUNCTION
    public Bug get(Integer id) throws ValueNotFoundException {

        try {
            return bugRepository.findById(id).get();
        } catch (NoSuchElementException exception) {
            throw new ValueNotFoundException("Could not find any bug with ID " + id);
        }
    }

    //SEARCH RESULTS
    public Page<Bug> listAllSearches(String keyword, Pageable pageable) {

        if (keyword != null) {
            return bugRepository.search(keyword, pageable);
        }
        return bugRepository.findAll(pageable);
    }

    //DELETE BUG BY ID
    public void deleteBug(Integer id) throws ValueNotFoundException {
        Integer countById = bugRepository.countByBugId(id);
                if (countById == null || countById == 0) {
            throw new ValueNotFoundException("Could not find any user with ID " + id);
        }

        Bug bug= bugRepository.getById(id);
        bugRepository.deleteById(id);

        System.out.println(bug.getLabel());
        Activity deleteActivity = new Activity(String.format
                ("Bug with Id %d with description %s initiated by %s, deleted", id ,bug.getBugReview(), bug.getCreatedBy()),
                "DELETED",
                new Date());
        deleteActivity.setBugActivity(bug);
        deleteActivity.setAction(Action.BUG_DELETION);
        activityRepository.save(deleteActivity);
    }


    //UPDATE BUG INFORMATION
    public ResponseEntity<Bug> updateBug(Bug bug) {
        return new ResponseEntity<>(bugRepository.save(bug),HttpStatus.ACCEPTED);
    }


    //UPDATE BUG REST CONTROLLER
    @Transactional
    public void updateBugRestController(
            Integer bugId,
            String treatmentStage,
            User userAssignedToBug,
            Severity enumSeverity){

        Bug existingBug =
                bugRepository.findById(bugId).orElseThrow(()->new IllegalStateException(
                        "bug with id " + bugId + " does not exist"));


        existingBug.setBugTreatmentStage(treatmentStage);
        existingBug.setLastUpdate(LocalDate.now());
        existingBug.setAssigned(true);
        existingBug.setUserAssignedToBug(userAssignedToBug);
        existingBug.setAssignedDate(String.valueOf(LocalDate.now()));
        existingBug.setEnumSeverity(enumSeverity);

    }


    //GET BUGS ASSIGNED T0 USER
    public List<Bug> getBugByUserId(int userId){
        return bugRepository.theAssignedBugsToAUser(userId);
    }


    public void updateAssignmentStatus(Integer id, boolean assigned) {
        bugRepository.updateAssignmentStatus(id, assigned);
    }

    public List<Bug> bugsSubmittedByUser(Integer userId){
        return bugRepository.theBugsSubmittedByAUser(userId);
    }

}
