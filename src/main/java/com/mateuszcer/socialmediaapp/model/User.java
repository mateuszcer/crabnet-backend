package com.mateuszcer.socialmediaapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
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
    private Long id;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    private String password;

    private String bio;

    private EGender gender;

    private Integer profilePicture; // ID of default picture stored on the frontend

    private Boolean enabled = Boolean.FALSE;

    @OneToMany(mappedBy="author")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "from")
    private Set<Likes> likedPosts;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
