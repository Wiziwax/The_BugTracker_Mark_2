package com.example.the_bugtracker_mark_2.Services;


import com.example.the_bugtracker_mark_2.Configs.ValueNotFoundException;
import com.example.the_bugtracker_mark_2.DTO.PlatformDTO;
import com.example.the_bugtracker_mark_2.Enums.PlatformStatus;
import com.example.the_bugtracker_mark_2.Models.Platforms;
import com.example.the_bugtracker_mark_2.Repositories.PlatformsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlatformService {

    @Autowired
    PlatformsRepository platformsRepository;


    //List all platforms
    public List<Platforms> platformsList(){
        return platformsRepository.findAll();
    }

    //List only available platforms
    public List<Platforms> availablePlatformsList(){
        return platformsRepository.activePlatformsList();
    }

    public List<Platforms> deactivatedPlatformsList(){
        return platformsRepository.deactivatedPlatforms();
    }

    public List<Platforms> platformsUnderTreatment(){
        return platformsRepository.platformsUnderTreatmentList();
    }


    public Platforms create(Platforms platform) {
        Platforms platforms1=new Platforms();
        platformsRepository.save(platform);
        return platforms1;
    }


    public Platforms get(Integer id) throws ValueNotFoundException {

        try {
            return platformsRepository.findById(id).get();
        } catch (NoSuchElementException exception) {
            throw new ValueNotFoundException("Could not find any platform with ID " + id);
        }
    }

    public void deletePlatform(Integer id) throws ValueNotFoundException {
        Integer countById = platformsRepository.countByPlatformId(id);
        if (countById == null || countById == 0) {
            throw new ValueNotFoundException("Could not find any user with ID " + id);
        }

        platformsRepository.deleteById(id);
    }

    public void deletePlatformRestController(Integer id){
        platformsRepository.deleteById(id);
    }

    public Platforms updatePlatform(Platforms platform) {
        return platformsRepository.save(platform);
    }


    @Transactional
    public void updatePlatformRestController(
            Integer platformId,
            String platformName,
            PlatformStatus platformStatus){

        Platforms existingPlatform=
                platformsRepository.findById(platformId).orElseThrow(()-> new IllegalStateException(
                        "platform with id " + platformId + "does not exist"));

        existingPlatform.setPlatformName(platformName);
        existingPlatform.setPlatformStatus(platformStatus);

    }



}