package com.mateuszcer.socialmediaapp.service;

import com.mateuszcer.socialmediaapp.model.Comment;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.model.UserPost;
import com.mateuszcer.socialmediaapp.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {
    private final CommentRepository commentRepository;

    private final CommentLikeService commentLikeService;

    public CommentsService(CommentRepository commentRepository, CommentLikeService commentLikeService) {
        this.commentRepository = commentRepository;
        this.commentLikeService = commentLikeService;
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> findAllByAuthor(User author) {
        return commentRepository.findAllByAuthor(author);
    }

    public List<Comment> findAllBySource(UserPost source) {
        return commentRepository.findAllBySource(source);
    }

    public Comment createComment(UserPost source, User author, String content) {
        Comment comment = new Comment(source, author, content);
        comment.setLikedBy(new HashSet<>());
        return commentRepository.save(comment);
    }

    public void deleteById(Long id) {
        Optional<Comment> commentOpt = findById(id);
        commentOpt.ifPresent(commentRepository::delete);

    }

    public void delete(Comment comment) {
        commentLikeService.deleteAllByComment(comment);
        commentRepository.delete(comment);

    }



}
