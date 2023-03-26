package com.mateuszcer.socialmediaapp.service;

import com.mateuszcer.socialmediaapp.model.Followers;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.repository.FollowersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowersService {

    private final FollowersRepository followersRepository;

    public FollowersService(FollowersRepository followersRepository) {
        this.followersRepository = followersRepository;
    }

    public List<Followers> getAllByTo(User user) {
        return followersRepository.findAllByTo(user);
    }

    public List<Followers> getAllByFrom(User user) {
        return followersRepository.findAllByTo(user);
    }

    public Followers update(Followers followers) {
        return followersRepository.save(followers);
    }
}
