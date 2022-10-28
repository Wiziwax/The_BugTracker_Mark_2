package com.example.the_bugtracker_mark_2.Repositories;

import com.example.the_bugtracker_mark_2.Models.Platforms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin(origins = "http://10.128.32.201:4200")
public interface PlatformsRepository extends JpaRepository<Platforms, Integer> {

    @Override
    public List<Platforms> findAll();

    public Integer countByPlatformId(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM bugtracker.platforms where platform_status=0;")
    public List<Platforms> activePlatformsList();

    @Query(nativeQuery = true, value = "SELECT * FROM bugtracker.platforms where platform_status=1;")
    public List<Platforms> platformsUnderTreatmentList();

    @Query(nativeQuery = true, value = "SELECT * FROM bugtracker.platforms where platform_status=2;")
    public List<Platforms> deactivatedPlatforms();

}
