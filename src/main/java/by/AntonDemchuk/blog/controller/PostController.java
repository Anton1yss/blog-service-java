package by.AntonDemchuk.blog.controller;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.PageDto;
import by.AntonDemchuk.blog.dto.post.PostDto;
import by.AntonDemchuk.blog.dto.post.PostReadDto;
import by.AntonDemchuk.blog.dto.post.PostSearchParamsDto;
import by.AntonDemchuk.blog.service.PostService;
import by.AntonDemchuk.blog.service.SharedService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "Post Controller")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}/")
    public ResponseEntity<PostReadDto> getPost(@PathVariable @NotNull Long postId) {
        return ResponseEntity.ok(postService.findById(postId));
    }

    @GetMapping(value = "/")
    public ResponseEntity<PageDto<PostReadDto>> getAllPosts(
            @ModelAttribute PostSearchParamsDto postSearchParamsDto,
            @ParameterObject @PageableDefault(size = 5, page = 0, sort = {}) Pageable pageable) {

        return ResponseEntity.ok(postService.findAll(postSearchParamsDto, pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PageDto<PostReadDto>> getAllUserPosts(
            @PathVariable @NotNull Long userId,
            @ParameterObject @PageableDefault(size = 5, page = 0, sort = {}) Pageable pageable){

        return ResponseEntity.ok(postService.findAllByUserId(pageable, userId));
    }

    @PostMapping("/")
    public ResponseEntity<PostDto> create(@RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.create(postDto));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> update(@RequestBody PostDto postDto,
                                          @PathVariable @NotNull Long postId) {
        return ResponseEntity.ok(postService.update(postDto, postId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDto> delete(@PathVariable @NotNull Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok().build();
    }
}
