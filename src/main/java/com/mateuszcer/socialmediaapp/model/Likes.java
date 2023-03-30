package com.mateuszcer.socialmediaapp.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="from_user_fk")
    private User from;

    @ManyToOne
    @JoinColumn(name="to_post_fk")
    private UserPost to;

    public Likes(User from, UserPost to) {
        this.from = from;
        this.to = to;
    }
}
