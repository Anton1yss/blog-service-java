package by.AntonDemchuk.blog.service;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.database.entity.Reaction;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        } else {
            throw new EntityNotFoundException("Reaction from User " + userId + " and Post " + postId + " not found");
        }
    }

    @Transactional(readOnly = true)
    public List<ReactionReadDto> findAllByPostId(@NotNull Long postId) {
        return reactionRepository.findAllByPostId(postId).stream()
                .map(reactionReadMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReactionReadDto findByPostIdAndUserId(@NotNull Long postId, @NotNull Long userId) {
        return reactionRepository.findByPostIdAndUserId(postId, userId)
                .map(reactionReadMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Reaction from User " + userId + " and Post " + postId + " not found"));
    }
}
