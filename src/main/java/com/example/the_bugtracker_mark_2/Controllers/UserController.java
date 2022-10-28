package com.example.the_bugtracker_mark_2.Controllers;

import com.example.the_bugtracker_mark_2.Configs.SecurityUser;
import com.example.the_bugtracker_mark_2.Configs.ValueNotFoundException;
import com.example.the_bugtracker_mark_2.Models.Bug;
import com.example.the_bugtracker_mark_2.Models.Role;
import com.example.the_bugtracker_mark_2.Models.User;
import com.example.the_bugtracker_mark_2.Repositories.UserRepository;
import com.example.the_bugtracker_mark_2.Services.BugService;
import com.example.the_bugtracker_mark_2.Services.PlatformService;
import com.example.the_bugtracker_mark_2.Services.RoleService;
import com.example.the_bugtracker_mark_2.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;
    @Autowired
    BugService bugService;

    @Autowired
    PlatformService platformService;




    @GetMapping("")
    public String displayUsers(Model model) {
        List<User> users = userService.listAll();
        model.addAttribute("users", users);
        return "users/list-users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {

        User aUser = new User();
        List<Role> roleList = roleService.listAllRoles();
        model.addAttribute("allRoles", roleList);
        model.addAttribute("user", aUser);
        return "users/new-users";
    }



    @PostMapping("save")
    public String createUserForm(User user, @RequestParam("image") MultipartFile multipartFile)
            throws IOException {

//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            user.setPhotos(fileName);
//            User savedUser = userRepository.save(user);
//            String uploadDir = "user-photos/" + savedUser.getId();
//            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        userService.save(user);
        return "redirect:/users/";
    }

    @GetMapping("edit/{id}")
//    @PreAuthorize("#userId == principal.id")
    public String editUser(@PathVariable Integer id, Model model) throws ValueNotFoundException {
        User existingUser = userService.get(id);
        List<Role> roleList = roleService.listAllRoles();
        model.addAttribute("allRoles", roleList);
        model.addAttribute("user", existingUser);
        return "users/edit_user";
    }

    @PostMapping("{id}")
    public String updateUser(@PathVariable Integer id,
                             @ModelAttribute("user") User user)
                             throws ValueNotFoundException {

        User existingUser = userService.get(id);
        existingUser.setId(user.getId());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPassword(user.getPassword());
        existingUser.setRoles(user.getRoles());
        existingUser.setEmail(user.getEmail());

        userService.updateUser(existingUser);

        return "redirect:/users/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUsers(@PathVariable Integer id) throws ValueNotFoundException {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id,
                                          @PathVariable("status") boolean enabled,
                                          RedirectAttributes redirectAttributes)
                                          throws ValueNotFoundException {
        userService.updateUserEnabledStatus(id, enabled);
        String user1 = userService.get(id).getFirstName();
        String status = enabled ? "enabled" : "disabled";
        String message = "The user ID " + id + " has been " + status;
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";
    }

    @GetMapping("developerdashboard/{id}")
    public String developerDashboard(@PathVariable Integer id,
                              @ModelAttribute("user") User user,
                              Model model) throws ValueNotFoundException {

        List<Bug> listBugByDeveloperAssigned = bugService.getBugByUserId(id);
        model.addAttribute("developerbuglist", listBugByDeveloperAssigned);
        return "/users/individualreporttable";
    }
}