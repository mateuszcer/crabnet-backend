package com.mateuszcer.socialmediaapp.service;

import com.mateuszcer.socialmediaapp.model.Followers;
import com.mateuszcer.socialmediaapp.model.Likes;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.model.UserPost;
import com.mateuszcer.socialmediaapp.repository.FollowersRepository;
import com.mateuszcer.socialmediaapp.repository.LikesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesService {

    private final LikesRepository likesRepository;

    public LikesService(LikesRepository likesRepository) {


        this.likesRepository = likesRepository;
    }

    public List<Likes> getAllByTo(UserPost userPost) {
        return likesRepository.findAllByTo(userPost);
    }

    public List<Likes> getAllByFrom(User user) {
        return likesRepository.findAllByFrom(user);
    }

    public Likes update(Likes likes) {
        return likesRepository.save(likes);
    }
}
