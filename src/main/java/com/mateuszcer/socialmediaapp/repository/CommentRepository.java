package com.mateuszcer.socialmediaapp.repository;

import com.mateuszcer.socialmediaapp.model.Comment;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.model.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllBySource(UserPost source);

    List<Comment> findAllByAuthor(User user);
}
