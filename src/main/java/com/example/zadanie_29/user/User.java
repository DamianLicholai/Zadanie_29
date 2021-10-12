package com.example.zadanie_29.user;


import javax.persistence.*;
import java.util.*;

import static com.example.zadanie_29.user.Role.ROLE_ADMIN;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private Set<UserRole> roles = new HashSet<>();



    private String passwordResetKey;

    public String getPasswordResetKey() {
        return passwordResetKey;
    }

    public void setPasswordResetKey(String passwordResetKey) {
        this.passwordResetKey = passwordResetKey;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public void addRole(UserRole userRole) {
        roles.add(userRole);
        userRole.setUser(this);
    }

    public void deleteRole (User user) {
        Set<UserRole> roles = user.getRoles();
        for (UserRole r: roles) {
            System.out.println(r.getRole());
        }
        boolean remove = roles.remove(ROLE_ADMIN);
        System.out.println(remove);
    }


}
