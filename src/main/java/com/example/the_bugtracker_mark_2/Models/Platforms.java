package com.example.the_bugtracker_mark_2.Models;



import com.example.the_bugtracker_mark_2.Enums.PlatformStatus;

import javax.persistence.*;

@Entity
@Table(name = "platforms")
public class Platforms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer platformId;

    private String platformName;


    private PlatformStatus platformStatus;


    public Platforms() {
    }

    public Platforms(String platformName) {
        this.platformName = platformName;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public PlatformStatus getPlatformStatus() {
        return platformStatus;
    }

    public void setPlatformStatus(PlatformStatus platformStatus) {
        this.platformStatus = platformStatus;
    }

    @Override
    public String toString(){
        return this.platformName;
    }
}
