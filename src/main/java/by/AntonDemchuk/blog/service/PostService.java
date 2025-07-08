package by.AntonDemchuk.blog.service;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.PostDto;
import by.AntonDemchuk.blog.dto.PostReadDto;
import by.AntonDemchuk.blog.mapper.PostMapper;
import by.AntonDemchuk.blog.mapper.PostReadMapper;
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
@Validated
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostReadMapper postReadMapper;
    private final UserRepository userRepository;

    public PostDto create(@Valid PostDto postDto, @NotNull Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with Id " + userId + " not found"));

        Post newPostToCreate = postMapper.toEntity(postDto);
        newPostToCreate.setUser(user);

        PostDto createdPost = postMapper.toDto(postRepository.save(newPostToCreate));
        log.info("Post from User {} created successfully", userId);

        return createdPost;
    }

    public void delete(@NotNull Long postId){
        Optional<Post> postToDelete = postRepository.findById(postId);

        if (postToDelete.isPresent()) {
            postRepository.delete(postToDelete.get());
            log.info("Post with Id {} deleted successfully", postId);
        } else{
            throw new EntityNotFoundException("Post with ID " + postId + " not found");
        }
    }

    public PostDto update(@Valid PostDto postToUpdate, @NotNull Long postId) {
        return postRepository.findById(postId)
                .map(entity -> postMapper.update(postToUpdate, entity))
                .map(postRepository::save)
                .map(postMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Post with ID " + postId + " not found"));
    }

    @Transactional(readOnly = true)
    public PostReadDto findById(@NotNull Long postId) {
        return postRepository.findById(postId)
                .map(postReadMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Post with ID " + postId + " not found"));
    }

    @Transactional(readOnly = true)
    public List<PostReadDto> findAllByUserId(@NotNull Long userId) {
        return postRepository.findAllByUserId(userId).stream()
                .map(postReadMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostReadDto> findAll() {
        return postRepository.findAll().stream()
                .map(postReadMapper::toDto)
                .collect(Collectors.toList());
    }
}
