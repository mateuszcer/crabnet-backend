package com.mateuszcer.socialmediaapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    private String password;

    private EGender gender;
    private Boolean enabled = Boolean.FALSE;


    @OneToMany(mappedBy="author")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy="author")
    private Set<UserPost> posts = new HashSet<>();

    @OneToMany(mappedBy="to")
    private Set<Followers> followers;

    @OneToMany(mappedBy="from")
    private Set<Followers> following;
}
