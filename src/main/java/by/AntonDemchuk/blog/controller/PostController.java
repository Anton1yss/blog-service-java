package by.AntonDemchuk.blog.controller;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.PostDto;
import by.AntonDemchuk.blog.dto.PostReadDto;
import by.AntonDemchuk.blog.dto.PostSearchParamsDto;
import by.AntonDemchuk.blog.service.PostService;
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

    @GetMapping("/{id}/")
    public ResponseEntity<PostReadDto> getPost(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @GetMapping(value = "/")
    public ResponseEntity<Page<PostReadDto>> getAllPosts(
            @ModelAttribute PostSearchParamsDto postSearchParamsDto,
            @ParameterObject @PageableDefault(size = 5, page = 0, sort = {}) Pageable pageable) {

        return ResponseEntity.ok(postService.findAll(postSearchParamsDto, pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<PostReadDto>> getAllUserPosts(
            @PathVariable @NotNull Long userId,
            @ParameterObject @PageableDefault(size = 5, page = 0, sort = {}) Pageable pageable){

        return ResponseEntity.ok(postService.findAllByUserId(pageable, userId));
    }

    @PostMapping("/")
    public ResponseEntity<PostDto> create(@RequestBody PostDto postDto, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.create(postDto, user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> update(@RequestBody PostDto postDto, @PathVariable @NotNull Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.update(postDto, id, user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostDto> delete(@PathVariable @NotNull Long id, @AuthenticationPrincipal User user) {
        postService.delete(id, user.getId());
        return ResponseEntity.ok().build();
    }
}
