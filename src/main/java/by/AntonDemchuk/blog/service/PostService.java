package by.AntonDemchuk.blog.service;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.PostDto;
import by.AntonDemchuk.blog.dto.PostReadDto;
import by.AntonDemchuk.blog.dto.PostSearchParamsDto;
import by.AntonDemchuk.blog.mapper.*;
import by.AntonDemchuk.blog.repository.PostRepository;
import by.AntonDemchuk.blog.repository.UserRepository;
import by.AntonDemchuk.blog.specification.PostSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final PostMapper postMapper;
    private final PostReadMapper postReadMapper;

    public PostDto create(@Valid PostDto postDto, @NotNull Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User (ID: " + userId + ") not found."));

        Post newPostToCreate = postMapper.toEntity(postDto);
        newPostToCreate.setUser(user);
        newPostToCreate.setCreationDate(LocalDateTime.now());

        PostDto createdPost = postMapper.toDto(postRepository.save(newPostToCreate));
        log.info("Post from User (ID: {}) created successfully.", userId);

        return createdPost;
    }

    public void delete(@NotNull Long postId, @NotNull Long userId) {
        Optional<Post> postToDelete = postRepository.findByIdAndUserId(postId, userId);

        if (postToDelete.isPresent()) {
            postRepository.delete(postToDelete.get());
            log.info("Post (ID: {}) deleted successfully.", postId);
        } else throw new EntityNotFoundException("Post (ID: " + postId + ") from User (ID: "+ userId + ") not found.");
    }

    public PostDto update(@Valid PostDto postToUpdate, @NotNull Long postId, @NotNull Long userId) {
        Optional <Post> existingPost = postRepository.findByIdAndUserId(postId, userId);

        if(existingPost.isPresent()){
            Post post = postMapper.update(postToUpdate, existingPost.get());
            postRepository.save(post);
            log.info("Post (ID: {}) updated successfully by user (ID: {}).", postId, userId);
            return postMapper.toDto(post);

        } throw new EntityNotFoundException("Post (ID: " + postId + ") from User (ID: "+ userId + ") not found.");
    }

    @Transactional(readOnly = true)
    public PostReadDto findById(@NotNull Long postId) {
        return postRepository.findById(postId)
                .map(postReadMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Post (ID: " + postId + ") not found"));
    }

    @Transactional(readOnly = true)
    public Page<PostReadDto> findAllByUserId(Pageable pageable, @NotNull Long userId) {

        if(userRepository.existsById(userId)){
            Page<Post> userPostsPage = postRepository.findAllByUserId(pageable, userId);
            return userPostsPage.map(postReadMapper::toDto);

        } else throw new EntityNotFoundException("User (ID: " + userId + ") not found.");
    }

    @Transactional(readOnly = true)
    public Page<PostReadDto> findAll(PostSearchParamsDto postSearchParamsDto, Pageable pageable) {

        Specification<Post> spec = PostSpecification.buildSpecification(postSearchParamsDto);

        Page<Post> postsPage = postRepository.findAll(spec, pageable);

        return postsPage.map(postReadMapper::toDto);
    }

}
