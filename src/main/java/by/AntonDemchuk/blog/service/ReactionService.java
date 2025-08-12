package by.AntonDemchuk.blog.service;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.database.entity.Reaction;
import by.AntonDemchuk.blog.database.entity.ReactionId;
import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.ReactionDto;
import by.AntonDemchuk.blog.dto.ReactionReadDto;
import by.AntonDemchuk.blog.mapper.ReactionMapper;
import by.AntonDemchuk.blog.mapper.ReactionReadMapper;
import by.AntonDemchuk.blog.repository.PostRepository;
import by.AntonDemchuk.blog.repository.ReactionRepository;
import by.AntonDemchuk.blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
@Slf4j
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final ReactionMapper reactionMapper;
    private final ReactionReadMapper reactionReadMapper;

    public ReactionDto create(@Valid ReactionDto reactionDto, @NotNull Long postId, @NotNull Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post with ID " + postId + " not found"));

        Reaction newReactionToCreate = reactionMapper.toEntity(reactionDto);

        newReactionToCreate.setId(
                ReactionId.builder().
                postId(postId).
                userId(userId).
                build());
        newReactionToCreate.setUser(user);
        newReactionToCreate.setPost(post);

        ReactionDto createdReaction = reactionMapper.toDto(reactionRepository.save(newReactionToCreate));
        log.info("Reaction from User {} for Post {} created successfully", userId, postId);

        return createdReaction;
    }

    public void delete(@NotNull Long postId, @NotNull Long userId) {
        Optional<Reaction> reactionToDelete = reactionRepository.findByPostIdAndUserId(postId, userId);

        if (reactionToDelete.isPresent()) {
            reactionRepository.delete(reactionToDelete.get());
            log.info("Reaction for User {}: and Post: {} deleted successfully", userId, postId);
        } else throw new EntityNotFoundException("Reaction from User " + userId + " and Post " + postId + " not found");
    }

    @Transactional(readOnly = true)
    public Page<ReactionReadDto> findAllByPostId(@NotNull Long postId, Pageable pageable) {

        if(postRepository.existsById(postId)) {
            Page<Reaction> reactionsPage = reactionRepository.findAllByPostId(postId, pageable);
            return reactionsPage.map(reactionReadMapper::toDto);

        }  else throw new EntityNotFoundException("Post with ID " + postId + " not found");
    }

    @Transactional(readOnly = true)
    public Long countAllByPostId(@NotNull Long postId) {

        if(postRepository.existsById(postId)) {
            return reactionRepository.countByPostId(postId);

        } else throw new EntityNotFoundException("Post with ID " + postId + " not found");
    }
}
