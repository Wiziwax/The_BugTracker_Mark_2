package com.example.the_bugtracker_mark_2.Controllers;


import com.example.the_bugtracker_mark_2.Configs.ValueNotFoundException;
import com.example.the_bugtracker_mark_2.Enums.Action;
import com.example.the_bugtracker_mark_2.Models.Activity;
import com.example.the_bugtracker_mark_2.Models.Bug;
import com.example.the_bugtracker_mark_2.Models.Platforms;
import com.example.the_bugtracker_mark_2.Models.User;
import com.example.the_bugtracker_mark_2.Repositories.ActivityRepository;
import com.example.the_bugtracker_mark_2.Repositories.UserRepository;
import com.example.the_bugtracker_mark_2.Services.ActivityService;
import com.example.the_bugtracker_mark_2.Services.BugService;
import com.example.the_bugtracker_mark_2.Services.PlatformService;
import com.example.the_bugtracker_mark_2.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("bug")
public class BugController {


    @Autowired
    BugService bugService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
    @Autowired
    PlatformService platformService;
    @Autowired
    ActivityService activityService;
    @Autowired
    ActivityRepository activityRepository;



    @GetMapping("")
    public String displayBugs(Model model) {
        List<Bug> bugs = bugService.bugList();
        model.addAttribute("bugs", bugs);
        return "bugs/list-bugs";
    }

    @GetMapping("/filtered")
    public String displayAssignedBugs(Model model) {
        List<Bug> assignedBugs = bugService.listAssignedBugs();
        model.addAttribute("assignedBugs", assignedBugs);
        return "bugs/assigned_bugs_list";
    }



    @GetMapping("pendingapprovals")
    public String displayPendingApprovals(Model model){
        List<Bug> pendingApprovals = bugService.listOfPendingApprovals();
        model.addAttribute("pendingApprovals", pendingApprovals);
        return "bugs/list_bugs_developer";
    }


    @GetMapping("/new")
    public String newBugForm(Model model) {
        Bug aBug = new Bug();
        List<Platforms> allPlatforms = platformService.platformsList();//Create API
        model.addAttribute("allPlatforms", allPlatforms);
        model.addAttribute("bug", aBug);
        return "bugs/new-bugs";
    }

    @PostMapping("/save")
    public String createBug(Bug bug){
//                            @AuthenticationPrincipal SecurityUser userDetails){
//        String userEmail=userDetails.getUsername();
//        User user = userRepository.getByEmail(userEmail);
//        bug.setCreatedBy(user.getFirstName()+ " " + user.getLastName());
        bugService.create(bug);
        return "redirect:/bug/";
    }


    @GetMapping("/edit/{id}")
    public String editBug(@PathVariable Integer id, Model model) throws ValueNotFoundException {
        Bug existingBug = bugService.get(id);
        List<Platforms> platformsList = platformService.platformsList();
        List<User> listUserByRole = userService.getUserByRoleId(2);
        model.addAttribute("developers", listUserByRole);
        model.addAttribute("allPlatforms", platformsList);
        model.addAttribute("bug", existingBug);
        return "bugs/edit_bug";
    }

    @PostMapping("{id}")
    public String updateBug(@PathVariable Integer id,
                            @ModelAttribute("bug") Bug bug
//                            ,@AuthenticationPrincipal SecurityUser userDetails
    ) throws ValueNotFoundException {


        Bug existingBug = bugService.get(id);

        existingBug.setSeverity(bug.getSeverity());
        existingBug.setBugTreatmentStage(bug.getBugTreatmentStage());
        existingBug.setLastUpdate(LocalDate.now());
        existingBug.setProgressStatus(bug.getProgressStatus());
        existingBug.setAssignedDate(String.valueOf(LocalDate.now()));
        existingBug.setUserAssignedToBug(assignmentResolution(bug, existingBug
//                userDetails
        ));
        existingBug.setAssigned(true);
        bugService.updateBug(existingBug);
        return "redirect:/bug/";
    }





    @GetMapping("delete/{id}")
    public String deleteBug(@PathVariable Integer id) throws ValueNotFoundException {
        bugService.deleteBug(id);

        return "redirect:/bug";
    }



    ///////////////////////////////////CONTROLLER UTILITY METHODS/////////////////////////////////////////

    public User assignmentResolution(Bug bug, Bug existingBug
//                                     SecurityUser userDetails
    ) {

        User assignedUser = null;

        if (existingBug.userAssignedToBug == null && existingBug.isAssigned()==false) {
            assignedUser = bugAssignment(bug, existingBug
//                    , userDetails
            );
            existingBug.setAssigned(true);
        }

        else if(!(existingBug.userAssignedToBug ==null && existingBug.isAssigned()==false)){
            existingBug.setUserAssignedToBug(null);
            existingBug.setAssigned(false);
            System.out.println("Sent to admin for approval");
        }





        return assignedUser;
    }

    public User bugAssignment(Bug bug, Bug existingBug
//            ,  SecurityUser userDetails
    ) {

//        String userFullName=userDetails.getFullName();
        existingBug.setUserAssignedToBug(bug.getUserAssignedToBug());
        String assignedTo = String.valueOf(bug.getUserAssignedToBug());
        Activity assignmentActivity = new Activity(
                existingBug.getCreatedBy(),
                bug.getReportDate(),
                null,
                String.format("Bug %s created by %s was assigned to %s ",
                        bug.getLabel(), existingBug.getCreatedBy(), bug.userAssignedToBug)
//                userFullName,
                ,null,
                assignedTo,
                bug.getBugTreatmentStage(),
                new Date()
        );
        assignmentActivity.setBugActivity(existingBug);
        assignmentActivity.setAction(Action.BUG_ASSIGNMENT);
        activityRepository.save(assignmentActivity);
        return existingBug.getUserAssignedToBug();
    }


    @GetMapping("activityonbug/{id}")
    public String bugActivityDashboard(@PathVariable Integer id,
                              @ModelAttribute("bug") Bug bug,
                              Model model)  {

        List<Activity> listOfActivitiesCarriedOutOnBug = activityService.getActivityByBugId(id);
        System.out.println(listOfActivitiesCarriedOutOnBug);
        model.addAttribute("bugactivitylist", listOfActivitiesCarriedOutOnBug);
        return "/bugs/bug_activity_table";
    }

    @PostMapping("reassignment/{id}")
    public String submitBugReassignment(@PathVariable Integer id,
                            @ModelAttribute("bug") Bug bug
//                            ,@AuthenticationPrincipal SecurityUser userDetails
    ) throws ValueNotFoundException {

        Bug existingBug = bugService.get(id);
        existingBug.setUserAssignedToBug(bug.getUserAssignedToBug());
        existingBug.setAssigned(true);
        bugService.updateBug(existingBug);
        ///SAVE REASSIGNMENT ACTIVITY
        Activity reassignmentActivity = new Activity(
                existingBug.getCreatedBy(),
                bug.getReportDate(),
                "Initially assigned to " + existingBug.userAssignedToBug,
                String.format("Bug %s created by %s was reassigned to %s ", bug.getLabel(),
                        existingBug.getCreatedBy(), bug.userAssignedToBug),
                new Date(),
//                userFullName,
                null,
                String.valueOf(bug.userAssignedToBug)
        );
        reassignmentActivity.setBugActivity(existingBug);
        reassignmentActivity.setAction(Action.BUG_REASSIGNMENT);
        activityRepository.save(reassignmentActivity);
        return "redirect:/bug/pendingapprovals";
    }


    @GetMapping("/reassignmentform/{id}")
    public String reassignmentForm(@PathVariable Integer id, Model model) throws ValueNotFoundException {
        Bug bugPendingReassignment = bugService.get(id);
        List<User> listUserByRole = userService.getUserByRoleId(2);
        model.addAttribute("developers", listUserByRole);
        model.addAttribute("bug", bugPendingReassignment);
        return "bugs/reassignment_form";
    }
        }
