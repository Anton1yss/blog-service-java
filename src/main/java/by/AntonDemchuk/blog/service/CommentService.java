package by.AntonDemchuk.blog.service;

import by.AntonDemchuk.blog.database.entity.Comment;
import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.CommentDto;
import by.AntonDemchuk.blog.dto.CommentReadDto;
import by.AntonDemchuk.blog.mapper.CommentMapper;
import by.AntonDemchuk.blog.mapper.CommentReadMapper;
import by.AntonDemchuk.blog.repository.CommentRepository;
import by.AntonDemchuk.blog.repository.PostRepository;
import by.AntonDemchuk.blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Validated
@Slf4j
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;
    private final CommentReadMapper commentReadMapper;

    public CommentDto create(@Valid CommentDto commentDto, @NotNull Long postId, @NotNull Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post with ID " + postId + " not found"));

        Comment comment = commentMapper.toEntity(commentDto);
        comment.setUser(user);
        comment.setPost(post);

        CommentDto createdComment = commentMapper.toDto(commentRepository.save(comment));
        log.info("Comment from User {} for Post {} created successfully", userId, postId);

        return createdComment;
    }

    public void delete(@NotNull Long commentId) {
        Optional<Comment> commentToDelete = commentRepository.findById(commentId);

        if (commentToDelete.isPresent()) {
            commentRepository.deleteById(commentId);
            log.info("Comment with ID {} deleted successfully", commentId);
        } else {
            throw new EntityNotFoundException("Comment with ID" + commentId + " not found.");
        }
    }

    public CommentDto update(@Valid CommentDto commentToUpdate, @NotNull Long commentId) {
        return commentRepository.findById(commentId)
                .map(entity -> commentMapper.update(commentToUpdate, entity))
                .map(commentRepository::save)
                .map(commentMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Comment with ID" + commentId + " not found."));
    }

    @Transactional(readOnly = true)
    public CommentReadDto findById(@NotNull Long commentId) {
        return commentRepository.findById(commentId)
                .map(commentReadMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Comment with ID" + commentId + " not found."));
    }

    @Transactional(readOnly = true)
    public List<CommentReadDto> findAllByPostId(@NotNull Long postId) {
        return commentRepository.findAllByPostId(postId).stream()
                .map(commentReadMapper::toDto)
                .collect(Collectors.toList());
    }
}
