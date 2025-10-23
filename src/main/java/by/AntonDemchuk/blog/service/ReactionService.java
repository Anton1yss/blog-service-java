package by.AntonDemchuk.blog.service;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.database.entity.Reaction;
import by.AntonDemchuk.blog.database.entity.ReactionId;
import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.PageDto;
import by.AntonDemchuk.blog.dto.reaction.ReactionDto;
import by.AntonDemchuk.blog.dto.reaction.ReactionReadDto;
import by.AntonDemchuk.blog.mapper.reaction.ReactionMapper;
import by.AntonDemchuk.blog.mapper.reaction.ReactionReadMapper;
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
    private final PostRepository postRepository;

    private final ReactionMapper reactionMapper;
    private final ReactionReadMapper reactionReadMapper;

    private final SharedService sharedService;

    public ReactionDto create(@Valid ReactionDto reactionDto, @NotNull Long postId) {

        User authrodizedUser = sharedService.getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post with ID " + postId + " not found"));

        Reaction newReactionToCreate = reactionMapper.toEntity(reactionDto);

        newReactionToCreate.setId(
                ReactionId.builder().
                postId(postId).
                userId(authrodizedUser.getId()).
                build());
        newReactionToCreate.setUser(authrodizedUser);
        newReactionToCreate.setPost(post);

        ReactionDto createdReaction = reactionMapper.toDto(reactionRepository.save(newReactionToCreate));
        log.info("Reaction from User {} for Post {} created successfully", authrodizedUser.getId(), postId);

        return createdReaction;
    }

    public void delete(@NotNull Long postId) {

        User authrodizedUser = sharedService.getCurrentUser();

        Optional<Reaction> reactionToDelete = reactionRepository.findByPostIdAndUserId(postId, authrodizedUser.getId());

        if (reactionToDelete.isPresent()) {
            reactionRepository.delete(reactionToDelete.get());
            log.info("Reaction for User {}: and Post: {} deleted successfully", authrodizedUser.getId(), postId);
        } else throw new EntityNotFoundException("Reaction from User " + authrodizedUser.getId() + " and Post " + postId + " not found");
    }

    @Transactional(readOnly = true)
    public PageDto<ReactionReadDto> findAllByPostId(@NotNull Long postId, Pageable pageable) {

        if(postRepository.existsById(postId)) {
            Page<Reaction> reactionsPage = reactionRepository.findAllByPostId(postId, pageable);
            return reactionReadMapper.toPageDto(reactionsPage);

        }  else throw new EntityNotFoundException("Post with ID " + postId + " not found");
    }

    @Transactional(readOnly = true)
    public Long countAllByPostId(@NotNull Long postId) {

        if(postRepository.existsById(postId)) {
            return reactionRepository.countByPostId(postId);

        } else throw new EntityNotFoundException("Post with ID " + postId + " not found");
    }
}
