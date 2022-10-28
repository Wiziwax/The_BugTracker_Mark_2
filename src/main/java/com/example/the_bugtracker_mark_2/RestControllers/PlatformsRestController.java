package com.example.the_bugtracker_mark_2.RestControllers;


import com.example.the_bugtracker_mark_2.Models.Bug;
import com.example.the_bugtracker_mark_2.Models.Platforms;
import com.example.the_bugtracker_mark_2.Configs.ValueNotFoundException;
import com.example.the_bugtracker_mark_2.Repositories.PlatformsRepository;
import com.example.the_bugtracker_mark_2.Services.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/platforms")
@CrossOrigin(origins = "http://10.128.32.201:4200")
public class PlatformsRestController {

    @Autowired
    PlatformService platformService;

    @Autowired
    PlatformsRepository platformsRepository;

    @GetMapping("")
    public List<Platforms> displayPlatforms(){
        return platformService.platformsList();
    }

    @GetMapping("activeplatforms")
    public List<Platforms> activePlatforms() {return platformService.availablePlatformsList();}

    @GetMapping("deactivatedplatforms")
    public List<Platforms> deactivatedPlatforms() {return platformService.deactivatedPlatformsList();}

    @GetMapping("platformsundertreatment")
    public List<Platforms> platformsUnderTreatment(){return platformService.platformsUnderTreatment();}


    @GetMapping("new")
    public void newPlatformForm(Model model){
        Platforms platform=new Platforms();
        List<Platforms>platformsList = platformService.platformsList();
        model.addAttribute("platform", platform);
    }

    @PostMapping("save")
    public Platforms createPlatform(Platforms platform){
        return platformsRepository.save(platform);
    }

    @PostMapping("{id}")
    public Platforms updatePlatform(@PathVariable Integer id,
                                 @ModelAttribute("platform") Platforms platform)
                                    throws ValueNotFoundException {
        Platforms existingPlatform = platformService.get(id);
        existingPlatform.setPlatformName(platform.getPlatformName());
        existingPlatform.setPlatformStatus(platform.getPlatformStatus());
        return platformService.updatePlatform(existingPlatform);
    }


    @PostMapping("updateplatform")
    public void updatePlatform(@RequestBody Platforms platforms) throws ValueNotFoundException {

        platformService.updatePlatformRestController(
                platforms.getPlatformId(),
                platforms.getPlatformName(),
                platforms.getPlatformStatus()
        );
    }

    //Delete Platform
    @PostMapping("delete")
    public void deletePlatforms(@RequestBody Platforms platforms) {
        Integer id = platforms.getPlatformId();
        platformService.deletePlatformRestController(id);
    }
}