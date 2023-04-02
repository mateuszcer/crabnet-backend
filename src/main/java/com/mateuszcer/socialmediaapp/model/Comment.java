package com.mateuszcer.socialmediaapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    private String content;

    @ManyToOne
    @JoinColumn(name="to_post")
    private UserPost source;

    @ManyToOne
    @JoinColumn(name="from_user")
    private User author;

    public Comment(UserPost source, User author, String content) {
        this.source = source;
        this.author = author;
        this.content = content;
    }

}
