package com.mateuszcer.socialmediaapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;


    private String content;


    @ManyToOne
    private UserPost source;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User author;

}
