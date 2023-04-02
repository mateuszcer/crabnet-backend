package com.mateuszcer.socialmediaapp.repository;

import com.mateuszcer.socialmediaapp.model.Followers;
import com.mateuszcer.socialmediaapp.model.Likes;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.model.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findAllByTo(UserPost userPost);
    List<Likes> findAllByFrom(User user);

    Boolean existsByFromAndTo(User user, UserPost userPost);

    Optional<Likes> findByFromAndTo(User user, UserPost userPost);


}
