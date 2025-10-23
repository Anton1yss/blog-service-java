package by.AntonDemchuk.blog.service;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.PageDto;
import by.AntonDemchuk.blog.dto.post.PostDto;
import by.AntonDemchuk.blog.dto.post.PostReadDto;
import by.AntonDemchuk.blog.dto.post.PostSearchParamsDto;
import by.AntonDemchuk.blog.mapper.post.PostMapper;
import by.AntonDemchuk.blog.mapper.post.PostReadMapper;
import by.AntonDemchuk.blog.repository.PostRepository;
import by.AntonDemchuk.blog.repository.UserRepository;
import by.AntonDemchuk.blog.repository.PostSpecification;
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

    private final SharedService sharedService;

    public PostDto create(@Valid PostDto postDto) {

        User authorizedUser = sharedService.getCurrentUser();

        Post newPostToCreate = postMapper.toEntity(postDto);
        newPostToCreate.setUser(authorizedUser);
        newPostToCreate.setCreationDate(LocalDateTime.now());

        PostDto createdPost = postMapper.toDto(postRepository.save(newPostToCreate));
        log.info("Post from User (ID: {}) created successfully.", authorizedUser.getId());

        return createdPost;
    }

    public void delete(@NotNull Long postId) {

        User authorizedUser = sharedService.getCurrentUser();

        Optional<Post> postToDelete = postRepository.findByIdAndUserId(postId, authorizedUser.getId());

        if (postToDelete.isPresent()) {
            postRepository.delete(postToDelete.get());
            log.info("Post (ID: {}) deleted successfully.", postId);
        } else throw new EntityNotFoundException("Post (ID: " + postId + ") from User (ID: "+ authorizedUser.getId() + ") not found.");
    }

    public PostDto update(@Valid PostDto postToUpdate, @NotNull Long postId) {

        User authorizedUser = sharedService.getCurrentUser();

        Optional <Post> existingPost = postRepository.findByIdAndUserId(postId, authorizedUser.getId());

        if(existingPost.isPresent()){
            Post post = postMapper.update(postToUpdate, existingPost.get());
            postRepository.save(post);
            log.info("Post (ID: {}) updated successfully by user (ID: {}).", postId, authorizedUser.getId());
            return postMapper.toDto(post);

        } throw new EntityNotFoundException("Post (ID: " + postId + ") from User (ID: "+ authorizedUser.getId() + ") not found.");
    }

    @Transactional(readOnly = true)
    public PostReadDto findById(@NotNull Long postId) {
        return postRepository.findById(postId)
                .map(postReadMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Post (ID: " + postId + ") not found"));
    }

    @Transactional(readOnly = true)
    public PageDto<PostReadDto> findAllByUserId(Pageable pageable, @NotNull Long userId) {

        if(userRepository.existsById(userId)){
            Page<Post> userPostsPage = postRepository.findAllByUserId(pageable, userId);
            return postReadMapper.toPageDto(userPostsPage);

        } else throw new EntityNotFoundException("User (ID: " + userId + ") not found.");
    }

    @Transactional(readOnly = true)
    public PageDto<PostReadDto> findAll(PostSearchParamsDto postSearchParamsDto, Pageable pageable) {

        Specification<Post> spec = PostSpecification.buildSpecification(postSearchParamsDto);

        Page<Post> postsPage = postRepository.findAll(spec, pageable);

        return postReadMapper.toPageDto(postsPage);

    }

}
