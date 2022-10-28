package com.example.the_bugtracker_mark_2.RestControllers;

import com.example.the_bugtracker_mark_2.Configs.SecurityUser;
import com.example.the_bugtracker_mark_2.Configs.ValueNotFoundException;
import com.example.the_bugtracker_mark_2.Enums.Action;
import com.example.the_bugtracker_mark_2.Models.Activity;
import com.example.the_bugtracker_mark_2.Models.Bug;
import com.example.the_bugtracker_mark_2.Models.Platforms;
import com.example.the_bugtracker_mark_2.Models.User;
import com.example.the_bugtracker_mark_2.Repositories.BugRepository;
import com.example.the_bugtracker_mark_2.Repositories.UserRepository;
import com.example.the_bugtracker_mark_2.Services.ActivityService;
import com.example.the_bugtracker_mark_2.Services.BugService;
import com.example.the_bugtracker_mark_2.Services.PlatformService;
import com.example.the_bugtracker_mark_2.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/bug")
@CrossOrigin(origins = "http://10.128.32.201:4200")
public class BugRestController {

    @Autowired
    BugService bugService;

    @Autowired
    UserService userService;


    @Autowired
    UserRepository userRepository;



    @Autowired
    PlatformService platformService;

    @Autowired
    ActivityService activityService;

    @Autowired
    BugRepository bugRepository;

    //LIST ALL BUGS
    @GetMapping("")
    public List<Bug> bugList(){
        return bugService.bugList();
    }


    //LIST BUG BY PAGE
    @CrossOrigin(origins = "http://10.128.32.201:4200")
    @GetMapping("pages")
    public ResponseEntity<Page<Bug>> viewBugPages(Pageable pageable){
        return new ResponseEntity<>(bugService.listAll(pageable), HttpStatus.ACCEPTED);
    }

    //LIST ASSIGNED BUGS
    @GetMapping("assignedbugs")
    public List<Bug> displayAssignedBugs(){
        return bugService.listAssignedBugs();
    }


    //SEARCH FILTER
    @GetMapping("search")
    @CrossOrigin(origins = "http://10.128.32.141:4200")
    public Page<Bug> viewHomePage(@RequestParam(name = "keyword", required = false) String keyword, Pageable pageable) {
        return bugService.listAllSearches(keyword, pageable);
    }


    //LIST PENDING APPROVALS(ADMIN ONLY)
    @GetMapping("pendingapprovals")
    public List<Bug>pendingApprovals(){
        return bugService.listOfPendingApprovals();
    }


    //CREATE BUG
    @PostMapping("addbug")
    public void createBug(@RequestBody Bug bug,
                          @AuthenticationPrincipal SecurityUser userDetails) {
        System.out.println("CreatedBug:" + bug);
        String userEmail=userDetails.getUsername();
        User user = userRepository.getByEmail(userEmail);

        bug.setCreatedBy(user.getId());
        bugService.create(bug);
    }


    //UPDATE BUG
//    @PostMapping("updatebug/{id}")
//    public void updateBug(@PathVariable Integer id,
//                          @RequestBody Bug bug)
////                          @AuthenticationPrincipal SecurityUser userDetails)
//                          throws ValueNotFoundException {
//
//        Bug existingBug = bugService.get(id);
//        existingBug.setBugTreatmentStage(bug.getBugTreatmentStage());
//        existingBug.setLastUpdate(LocalDate.now());
//        existingBug.setUserAssignedToBug(assignmentResolution(bug, existingBug
////                , userDetails
//        ));
//        existingBug.setAssignedDate(String.valueOf(LocalDate.now()));
//        existingBug.setEnumSeverity(bug.getEnumSeverity());
//        existingBug.setAssigned(true);
//        bugService.updateBug(existingBug);
//    }

    @PutMapping("updatebug")
    public void updateBug(@RequestBody Bug bug) throws ValueNotFoundException {
        bugService.updateBugRestController(
                bug.getBugId(),
                bug.getBugTreatmentStage(),
                bug.getUserAssignedToBug(),
                bug.getEnumSeverity()
        );
    }

    //DELETE BUG
    @GetMapping("delete")
    public void deleteBug(@RequestBody Bug bug) throws ValueNotFoundException {
        Integer id = bug.getBugId();
        bugService.deleteBug(id);
    }

    //FIND BUG BY ID
    @GetMapping("{id}")
    public Optional<Bug> findBugById(@PathVariable(name = "id") Integer id){
        return bugRepository.findById(id);
    }


    //REQUEST FOR LIST OF USERS ACCORDING TO THEIR ROLES, VIA ID(1, 2, or 3)
    @GetMapping("findusersonrole")
    public List<User> getByRole(@RequestParam(name = "roleId")Integer roleId){
        return userService.getUserByRoleId(roleId);
    }

    //PLATFORM LIST
    @GetMapping("platformlist")
    public List<Platforms> getPlatforms(){
        return platformService.platformsList();
    }

    //LIST OF ACTIVITIES CARRIED OUT ON BUG(ADMIN ONLY)
    @GetMapping("activityonbug/id")
    public List<Activity> bugActivityList(@PathVariable Integer id){
        return activityService.getActivityByBugId(id);
    }











/////////////////////////////////BUG UTILITIES/////////////////////////////////




    public User assignmentResolution(Bug bug, Bug existingBug
                                     ,SecurityUser userDetails
    ) {

        User assignedUser = null;

        if (existingBug.userAssignedToBug == null && existingBug.isAssigned()==false) {
            assignedUser = bugAssignment(bug, existingBug
                    , userDetails
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
            ,  SecurityUser userDetails
    ) {

        String userFullName=userDetails.getFullName();
        existingBug.setUserAssignedToBug(bug.getUserAssignedToBug());
        String assignedTo = String.valueOf(bug.getUserAssignedToBug());
        Activity assignmentActivity = new Activity(
                bug.getCreatedBy(),
                bug.getReportDate(),
                null,
                String.format("Bug %s created by %s was assigned to %s ",
                        bug.getLabel(), existingBug.getCreatedBy(), bug.userAssignedToBug),
                userFullName,
                assignedTo,
                bug.getBugTreatmentStage(),
                new Date()
        );
        assignmentActivity.setBugActivity(existingBug);
        assignmentActivity.setAction(Action.BUG_ASSIGNMENT);
        activityService.createActivity(assignmentActivity);
        return existingBug.getUserAssignedToBug();
    }






    @PostMapping("reassignment/{id}")
    public ResponseEntity<Bug> submitBugReassignment(@PathVariable Integer id,
                                                     @ModelAttribute("bug") Bug bug,
                                                     @AuthenticationPrincipal SecurityUser userDetails
    ) throws ValueNotFoundException {

        Bug existingBug = bugService.get(id);
        String userFullName=userDetails.getFullName();
        existingBug.setUserAssignedToBug(bug.getUserAssignedToBug());
        existingBug.setAssigned(true);

        ///SAVE REASSIGNMENT ACTIVITY
        Activity reassignmentActivity = new Activity(
                bug.getCreatedBy(),
                bug.getReportDate(),
                "Initially assigned to " + existingBug.userAssignedToBug,
                String.format("Bug %s created by %s was reassigned to %s ", bug.getLabel(),
                        existingBug.getCreatedBy(), bug.userAssignedToBug),
                new Date(),
                userFullName,
                String.valueOf(bug.userAssignedToBug)
        );
        reassignmentActivity.setBugActivity(existingBug);
        reassignmentActivity.setAction(Action.BUG_REASSIGNMENT);
        activityService.createActivity(reassignmentActivity);
        return bugService.updateBug(existingBug);
    }


    @GetMapping("bugrequestsubmitted")
    public List<Bug> BugsSubmittedByUser(@RequestParam Integer userId){
        return bugService.bugsSubmittedByUser(userId);
    }

    @GetMapping("mySubmittedBugs")
    public List<Bug> BugsSubmittedByUser(@AuthenticationPrincipal SecurityUser userDetails){

        String userEmail=userDetails.getUsername();
        User user = userRepository.getByEmail(userEmail);
        int userId = user.getId();
        return bugService.bugsSubmittedByUser(userId);
    }



}
