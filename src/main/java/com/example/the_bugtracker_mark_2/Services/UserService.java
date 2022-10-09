package com.example.the_bugtracker_mark_2.Services;

import com.example.the_bugtracker_mark_2.Models.User;
import com.example.the_bugtracker_mark_2.Repositories.UserRepository;
import com.example.the_bugtracker_mark_2.Configs.ValueNotFoundException;
import com.example.the_bugtracker_mark_2.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private BugRepository bugRepository;


    public List<User> listAll() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id){
        return userRepository.findById(id);
    }


    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

    public User get(Integer id) throws ValueNotFoundException {

        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException exception) {
            throw new ValueNotFoundException("Could not find any user with ID " + id);
        }
    }

    public void deleteUserNormalController(Integer id) throws ValueNotFoundException {
        Long countById = userRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new ValueNotFoundException("Could not find any user with ID " + id);
        }
        userRepository.deleteById(id);
    }



    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        userRepository.updateEnabledStatus(id, enabled);
    }

    public List<User> getUserByRoleId(int roleId){
        return userRepository.userRoleList(roleId);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void updateUserRestController(Integer userId, String firstName, String password,
                                         String lastName, String email){
        User user =
                userRepository.findById(userId).orElseThrow(()->new IllegalStateException(
                        "student with id " + userId + " does not exist"));



        if(firstName != null && firstName.length()>0 && !Objects.equals(user.getFirstName(), firstName)){
            user.setFirstName(firstName);
        }

        if(lastName != null && lastName.length()>0 && !Objects.equals(user.getLastName(), lastName)){
                    user.setLastName(lastName);
                }

        if(email != null && email.length()>0 && !Objects.equals(user.getEmail(), email)){
            Optional<User> userOptional = userRepository.findUserByEmail(email);
            if(userOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            user.setEmail(email);
        }
    }


}