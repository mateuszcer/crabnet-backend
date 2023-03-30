package com.mateuszcer.socialmediaapp.service;

import com.mateuszcer.socialmediaapp.model.Likes;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.model.UserPost;
import com.mateuszcer.socialmediaapp.repository.UserPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserPostService {

    private final UserPostRepository userPostRepository;

    private final LikesService likesService;

    public UserPostService(UserPostRepository userPostRepository, LikesService likesService) {
        this.userPostRepository = userPostRepository;
        this.likesService = likesService;
    }

    public final Optional<UserPost> getById(Long id) {
        return userPostRepository.findById(id);
    }


    public final UserPost likePost(UserPost userPost, User user) {
        Likes likes = new Likes(user, userPost);
        return likesService.update(likes).getTo();
    }

    public UserPost createPost(String content, User author) {

        UserPost userPost = UserPost.builder()
                .content(content)
                .author(author)
                .build();
        System.out.println(userPost.getLikedBy());
        return this.userPostRepository.save(
                userPost);
    }

    public List<UserPost> getByUser(User user) {
        return userPostRepository.findAllByAuthor(user);
    }

    public List<UserPost> getNewest(User author) {
        return userPostRepository.findFirst25ByAuthor(author);
    }

}
