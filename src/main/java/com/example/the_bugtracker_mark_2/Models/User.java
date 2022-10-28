package com.example.the_bugtracker_mark_2.Models;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    //DECLARATIONS
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128, nullable = false, unique = true)
    private String email;

    @Column(length = 64)
    private String password;

    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @Column(length = 64)
    private String photos;

    @Column
    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "user_team_id")
    private Teams userTeam;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;



//    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
//            CascadeType.REFRESH, CascadeType.PERSIST},
//            fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "user_bug",
//            joinColumns = @JoinColumn(name = "user_bug_id"),
//            inverseJoinColumns = @JoinColumn(name = "bug_user_id")
//    )
//    private List<Bug> bugToWhichUserWasAssigned;


//    @OneToOne(mappedBy = "userAssignedToBug")
//    private Bug bugForUser;

//    @OneToMany
//    private List<Bug> bugToWhichUserWasAssigned;

    //CONSTRUCTORS
    public User() {
    }

    public User(String name) {
    }

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    //GETTERS AND SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    @OneToOne
    @JoinColumn(name = "user_role_id", referencedColumnName = "roleId" )
    private Role roles;


    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public User(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }



    public User(String email, String password, String firstName,
                String lastName, String photos, boolean enabled) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photos = photos;
        this.enabled = enabled;

    }

    public Teams getUserTeam() {
        return userTeam;
    }

    public void setUserTeam(Teams userTeam) {
        this.userTeam = userTeam;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    @Override
    public String toString() {
        return firstName;
    }

    @Transient
    public String getPhotosImagePath() {
        if (id == null || photos == null) {

            return "/images/default-user.png";
        }
        return "/user-photos/" + this.id + "/" + this.photos;
    }


}


