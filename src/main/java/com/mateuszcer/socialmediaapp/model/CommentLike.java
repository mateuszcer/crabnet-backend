package com.mateuszcer.socialmediaapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CommentLike {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="from_user_cl")
    private User from;

    @ManyToOne
    @JoinColumn(name="to_comment_cl")
    private Comment to;

    public CommentLike(User from, Comment to) {
        this.from = from;
        this.to = to;
    }
}
