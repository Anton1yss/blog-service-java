package by.AntonDemchuk.blog.service;

import by.AntonDemchuk.blog.database.entity.Comment;
import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.PageDto;
import by.AntonDemchuk.blog.dto.comment.CommentDto;
import by.AntonDemchuk.blog.dto.comment.CommentReadDto;
import by.AntonDemchuk.blog.mapper.comment.CommentMapper;
import by.AntonDemchuk.blog.mapper.comment.CommentReadMapper;
import by.AntonDemchuk.blog.repository.CommentRepository;
import by.AntonDemchuk.blog.repository.PostRepository;
import by.AntonDemchuk.blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

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

    private final SharedService sharedService;

    public CommentDto create(@Valid CommentDto commentDto, @NotNull Long postId) {

        User authorizedUser = sharedService.getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post (ID: " + postId + ") not found."));

        Comment comment = commentMapper.toEntity(commentDto);
        comment.setUser(authorizedUser);
        comment.setPost(post);

        CommentDto createdComment = commentMapper.toDto(commentRepository.save(comment));
        log.info("Comment from User (ID: {}) for Post (ID: {}) created successfully.", authorizedUser.getId(), postId);

        return createdComment;
    }

    public void delete(@NotNull Long commentId) {

        User authorizedUser = sharedService.getCurrentUser();

        Optional<Comment> commentToDelete = commentRepository.findByIdAndUserId(commentId, authorizedUser.getId());

        if (commentToDelete.isPresent()) {
            commentRepository.deleteById(commentId);
            log.info("Comment (ID: {}) deleted successfully", commentId);
        } else {
            throw new EntityNotFoundException("Comment (ID: " + commentId + ") not found.");
        }
    }

    public CommentDto update(@Valid CommentDto commentToUpdate, @NotNull Long commentId) {

        User authorizedUser = sharedService.getCurrentUser();

        Optional<Comment> existingComment = commentRepository.findByIdAndUserId(commentId, authorizedUser.getId());

        if(existingComment.isPresent()){
            Comment comment = commentMapper.update(commentToUpdate, existingComment.get());
            commentRepository.save(comment);
            log.info("Comment (ID: {}) updated successfully by user (ID: {}).", commentId, authorizedUser.getId());
            return commentMapper.toDto(comment);

        } throw new EntityNotFoundException("Comment (ID: " + commentId + ") from User (ID: "+ authorizedUser.getId() + ") not found.");
    }

    @Transactional(readOnly = true)
    public CommentReadDto findById(@NotNull Long commentId) {
        return commentRepository.findById(commentId)
                .map(commentReadMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Comment (ID: " + commentId + ") not found."));
    }

    @Transactional(readOnly = true)
    public PageDto<CommentReadDto> findAllByPostId(@NotNull Long postId, Pageable pageable) {

        if(postRepository.existsById(postId)) {
            Page<Comment> commentsPage = commentRepository.findAllByPostId(postId, pageable);
            return commentReadMapper.toPageDto(commentsPage);

        } else throw new EntityNotFoundException("Post (ID: " + postId + ") not found.");
    }

    @Transactional(readOnly = true)
    public PageDto<CommentReadDto> findAllByUserId(Pageable pageable, @NotNull Long userId) {

        if(userRepository.existsById(userId)) {
            Page<Comment> commentsPage = commentRepository.findAllByUserId(userId, pageable);
            return commentReadMapper.toPageDto(commentsPage);

        } else throw new EntityNotFoundException("User (ID: " + userId + ") not found.");

    }
}
