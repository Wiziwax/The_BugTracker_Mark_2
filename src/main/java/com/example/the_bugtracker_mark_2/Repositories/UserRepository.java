package com.example.the_bugtracker_mark_2.Repositories;

import com.example.the_bugtracker_mark_2.Models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Repository
@CrossOrigin(origins = "http://10.128.32.141:4200")
public interface UserRepository extends CrudRepository<User, Integer> {

    @Override
    public List<User> findAll();

    public Long countById(Integer id);

    @Modifying
    @Query("UPDATE User u SET u.enabled=?2 WHERE u.id=?1")
    public void updateEnabledStatus(Integer id, boolean enabled);

    @Query(value = "SELECT * FROM USERS u WHERE u.user_role_id=:roleId", nativeQuery = true)
    public List<User> userRoleList(int roleId);

    Optional<User> findUserByEmail(String email);

    List<User> findByEmail(String email);

    User getByEmail(String userEmail);
}