package com.mateuszcer.socialmediaapp.repository;

import com.mateuszcer.socialmediaapp.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    List<CommentLike> findAllByTo(Comment comment);
    List<CommentLike> findAllByFrom(User user);

    Boolean existsByFromAndTo(User user, Comment comment);

    Optional<CommentLike> findByFromAndTo(User user, Comment comment);
}
