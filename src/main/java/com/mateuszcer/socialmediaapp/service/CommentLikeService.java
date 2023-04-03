package com.mateuszcer.socialmediaapp.service;

import com.mateuszcer.socialmediaapp.model.*;
import com.mateuszcer.socialmediaapp.repository.CommentLikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    public CommentLikeService(CommentLikeRepository commentLikeRepository) {


        this.commentLikeRepository = commentLikeRepository;
    }

    public List<CommentLike> getAllByTo(Comment comment) {

        return commentLikeRepository.findAllByTo(comment);
    }

    public List<CommentLike> getAllByFrom(User user) {
        return commentLikeRepository.findAllByFrom(user);
    }

    public CommentLike update(CommentLike commentLike) {
        return commentLikeRepository.save(commentLike);
    }

    public void delete(CommentLike commentLike) {
        commentLikeRepository.delete(commentLike);
    }

    public Boolean existsByFromAndTo(User user, Comment comment) {
        return commentLikeRepository.existsByFromAndTo(user, comment);
    }

    public Optional<CommentLike> findByFromAndTo(User user, Comment comment) {
        return commentLikeRepository.findByFromAndTo(user, comment);
    }

    public List<CommentLike> findAllByTo(Comment comment) {
        return commentLikeRepository.findAllByTo(comment);
    }

    public void deleteAllByComment(Comment comment) {
        List<CommentLike> toDelete = findAllByTo(comment);
        commentLikeRepository.deleteAll(toDelete);
    }

    public final Boolean likeComment(Comment comment, User user) {
        if(commentLikeRepository.existsByFromAndTo(user, comment)) {
            return Boolean.FALSE;
        }
        CommentLike commentLike = new CommentLike(user, comment);
        commentLikeRepository.save(commentLike);
        return Boolean.TRUE;
    }

    public final Boolean dislikeComment(Comment comment, User user) {
        Optional<CommentLike> commentLikeOpt = commentLikeRepository.findByFromAndTo(user, comment);

        if(commentLikeOpt.isPresent()) {
            commentLikeRepository.delete(commentLikeOpt.get());
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public void deleteAll(Set<CommentLike> commentLikes) {
        commentLikeRepository.deleteAll(commentLikes);
    }
}
