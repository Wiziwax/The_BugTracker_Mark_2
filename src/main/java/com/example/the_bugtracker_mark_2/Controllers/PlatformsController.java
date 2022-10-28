package com.example.the_bugtracker_mark_2.Controllers;

import com.example.the_bugtracker_mark_2.Configs.ValueNotFoundException;
import com.example.the_bugtracker_mark_2.DTO.PlatformDTO;
import com.example.the_bugtracker_mark_2.Models.Platforms;
import com.example.the_bugtracker_mark_2.Repositories.PlatformsRepository;
import com.example.the_bugtracker_mark_2.Services.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("platforms")
public class PlatformsController {

    @Autowired
    PlatformService platformService;
    @Autowired
    PlatformsRepository platformsRepository;


    @GetMapping("")
    public String displayPlatforms(Model model) {
        List<Platforms> platforms = platformService.platformsList();
        model.addAttribute("platforms", platforms);
        return "platforms/list-platforms";
    }


    @GetMapping("/new")
    public String newPlatformForm(Model model) {
        Platforms platform = new Platforms();
        List<Platforms> platformsList = platformService.platformsList();
        model.addAttribute("platform", platform);
        return "platforms/new-platform";
    }

    @PostMapping("/platformsave")
    public String createPlatform(Platforms platform) {
        platformService.create(platform);
        return "redirect:/platforms/";
    }



    @PostMapping("{id}")
    public String updatePlatform(@PathVariable Integer id,
                                 @ModelAttribute("platform") Platforms platform
                                 ) throws ValueNotFoundException {
        Platforms existingPlatform = platformService.get(id);
        existingPlatform.setPlatformStatus(platform.getPlatformStatus());
        existingPlatform.setPlatformName(platform.getPlatformName());
        platformsRepository.save(existingPlatform);
        return "redirect:/platforms/";
    }

    @GetMapping("/delete/{id}")
    public String deletePlatforms(@PathVariable Integer id) throws ValueNotFoundException {
        platformService.deletePlatform(id);
        return "redirect:/platforms";
    }


    @GetMapping("edit/{id}")
    public String editPlatform(@PathVariable Integer id, Model model) throws ValueNotFoundException {
        Platforms existingPlatforms = platformService.get(id);
        model.addAttribute("platforms", existingPlatforms);
        return "platforms/edit_platform";
    }

}
