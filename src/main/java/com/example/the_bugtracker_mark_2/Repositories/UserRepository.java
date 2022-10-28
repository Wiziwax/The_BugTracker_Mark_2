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
@CrossOrigin(origins = "http://10.128.32.201:4200")
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



//    @Query(nativeQuery = true, value = "SELECT * FROM users u WHERE u.email=:email  AND u.password=:password")
//    public User ifUserExists(String email, String password);

    public User findByEmailAndPassword(String email, String password);


    public User getByEmail(String userEmail);

    boolean existsByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM USERS u WHERE u.user_team_id=:teamId")
    public List<User> getUsersByUserTeam(int teamId);

    public User findByEmail(String email);
    public User findByPassword(String password);


    public User findByResetPasswordToken(String token);

}