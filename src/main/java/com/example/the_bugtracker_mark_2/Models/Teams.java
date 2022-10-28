package com.example.the_bugtracker_mark_2.Models;

import javax.persistence.*;

@Entity
@Table(name = "teams")
public class Teams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamId;

    @Column
    private String teamName;

    @OneToOne
    @JoinColumn(name = "team_platform")
    private Platforms teamPlatform;



    public Teams() {
    }

    public Teams(String teamName, Platforms teamPlatform) {
        this.teamName = teamName;
        this.teamPlatform = teamPlatform;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamsId) {
        this.teamId = teamsId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamsName) {
        this.teamName = teamsName;
    }

    public Platforms getTeamPlatform() {
        return teamPlatform;
    }

    public void setTeamPlatform(Platforms teamPlatform) {
        this.teamPlatform = teamPlatform;
    }

    @Override
    public String toString() {
        return teamName;
    }
}
