package com.mateuszcer.socialmediaapp.service;

import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.model.UserPost;
import com.mateuszcer.socialmediaapp.payload.request.PostCreationRequest;
import com.mateuszcer.socialmediaapp.repository.UserPostRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserPostService {

    private final UserPostRepository userPostRepository;

    public UserPostService(UserPostRepository userPostRepository) {
        this.userPostRepository = userPostRepository;
    }

    public final Optional<UserPost> getById(Long id) {
        return userPostRepository.findById(id);
    }


    public final UserPost likePost(UserPost userPost, User user) {
        Set<User> likedBy = userPost.getLikedBy();
        likedBy.add(user);
        userPost.setLikedBy(likedBy);
        return userPostRepository.save(userPost);
    }

    public UserPost createPost(String content, User author) {
        return this.userPostRepository.save(
                UserPost.builder()
                        .content(content)
                        .author(author)
                        .build());
    }

    public List<UserPost> getByUser(User user) {
        return userPostRepository.findAllByAuthor(user);
    }
}
