package com.example.the_bugtracker_mark_2.Repositories;

import com.example.the_bugtracker_mark_2.Models.Role;
import com.example.the_bugtracker_mark_2.DTO.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin(origins = "http://10.128.32.141:4200")
public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Override
    public List<Role> findAll();

    @Query(nativeQuery = true, value = "SELECT u.user_role_id as name, COUNT(*) as userCount " +
            "FROM tracktesty.users u " +
            "GROUP BY u.user_role_id " +
            "ORDER by u.user_role_id")
    public List<RoleUser> roleUsers();


}
