package com.mateuszcer.socialmediaapp.repository;

import com.mateuszcer.socialmediaapp.model.Followers;
import com.mateuszcer.socialmediaapp.model.Likes;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.model.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    public List<Likes> findAllByTo(UserPost userPost);
    public List<Likes> findAllByFrom(User user);
}
