package com.example.the_bugtracker_mark_2.Repositories;

import com.example.the_bugtracker_mark_2.DTO.ChartData;
import com.example.the_bugtracker_mark_2.Models.Bug;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@Repository
@CrossOrigin(origins = "http://10.128.32.201:4200")
public interface BugRepository extends JpaRepository<Bug,Integer> {

    @Query(nativeQuery = true, value = "SELECT bug_treatment_stage as label, " +
            "COUNT(*) as value FROM bug_information GROUP BY bug_treatment_stage")
    public List<ChartData> getProjectStatus();



    public Integer countByBugId(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM bug_information bu WHERE bu.user_bug_id=:userId and assigned=1")
    public List<Bug> theAssignedBugsToAUser(int userId);


    @Query(nativeQuery = true, value = "SELECT * FROM bug_information ud WHERE ud.created_by =:userId")
    public List<Bug> theBugsSubmittedByAUser(int userId);

//    @Query(nativeQuery = true, value = "SELECT * FROM bug_information bu WHERE bu.created_by=:userId")
//    public List<Bug> theBugsSubmittedByAUser(int userId);

    @Query(nativeQuery = true, value = "SELECT * FROM bug_information p WHERE p.severity LIKE %?1%")
    public Page<Bug> search(String keyword, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM bugtracker.bug_information WHERE user_bug_id IS null and assigned = 1")
    public List<Bug> listOfPendingApprovals();


    List<Bug> findBugByAssignedTrue();



    List<Bug> getBugByUserAssignedToBugIsNotNull();

    @Modifying
    @Query("UPDATE Bug bq SET bq.assigned=?2 WHERE bq.bugId=?1")
    public void updateAssignmentStatus(Integer id, boolean enabled);
}

