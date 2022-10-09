package com.example.the_bugtracker_mark_2.Models;


import javax.persistence.*;

@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(length = 40, nullable = false, unique = true)
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }



    public Role(Integer roleId, String name, String description) {
        this.roleId = roleId;
        this.name = name;

    }

    public Integer getRoleId() {
        return roleId;
    }

    public Role(Integer roleId) {
        this.roleId = roleId;
    }

    //GETTERS AND SETTERS
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

//
//    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
//            CascadeType.REFRESH, CascadeType.PERSIST},
//            fetch = FetchType.LAZY)
//    @JoinTable(name = "users_roles",
//            joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private List<User> roles;


//    @OneToMany(mappedBy = "roles")
//    private List<User> roleUser;


//    public List<User> getRoleUser() {
//        return roleUser;
//    }
//
//    public void setRoleUser(List<User> roleUser) {
//        this.roleUser = roleUser;
//    }

    @Override
    public String toString() {
        return this.name;
    }

}
