package by.AntonDemchuk.blog.controller;

import by.AntonDemchuk.blog.database.entity.User;
import by.AntonDemchuk.blog.dto.PageDto;
import by.AntonDemchuk.blog.dto.comment.CommentDto;
import by.AntonDemchuk.blog.dto.comment.CommentReadDto;
import by.AntonDemchuk.blog.dto.post.PostDto;
import by.AntonDemchuk.blog.service.CommentService;
import by.AntonDemchuk.blog.service.SharedService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
@Tag(name = "Comment Controller")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<PageDto<CommentReadDto>> getAllByPostId(
            @ParameterObject @PageableDefault(size = 5, page = 0, sort = {}) Pageable pageable,
            @PathVariable @NotNull Long postId) {

        return ResponseEntity.ok(commentService.findAllByPostId(postId, pageable));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PageDto<CommentReadDto>> getAllByUserId(
            @ParameterObject @PageableDefault(size = 5, page = 0, sort = {}) Pageable pageable,
            @PathVariable Long userId) {

        return ResponseEntity.ok(commentService.findAllByUserId(pageable, userId));
    }

    @PostMapping("/")
    public ResponseEntity<CommentDto> create(
            @RequestBody @Valid CommentDto commentDto,
            @RequestParam Long postId) {

        return ResponseEntity.ok(commentService.create(commentDto, postId));
    }

    @DeleteMapping("/")
    public ResponseEntity<PostDto> delete(
            @RequestParam @NotNull Long id) {

        commentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    public ResponseEntity<CommentDto> update(
            @RequestParam Long id,
            @RequestBody CommentDto commentDto){

        return ResponseEntity.ok(commentService.update(commentDto, id));
    }
}
